# JOINS:

## 1) Joins
- Joins are a way to combine data from multiple tables.
- Think of joins as a way to stitch rows of 2 tables together, based on the condition you specify. 
- A `join condition` is a condition that must be true between the rows of 2 tables for
  them to be stitched together.
- Imagine that every row of table1, we try to match with every row of batches. These 
  two rows are stitched only if the specified join condition is true. 
```sql
SELECT f.title, l.name
FROM film f
JOIN language l
ON f.language_id = l.language_id;
```
- This is the way to write the JOIN query. The aliases help us not type table names multiple times.
- This above query is an `INNER JOIN`, as it only joins the matching rows from two tables.

## 2) Self Join
```sql
SELECT s1.name, s2.name
FROM students s1
         JOIN students s2
              ON s1.buddy_id = s2.id;
```
- A self join is a join where we are joining a table with itself. In self joining, 
  aliasing tables is very important. If we don't alias the tables, then `SQL` will not
  know which row of the table to match with which row of the same table (because both 
  of them have same names as they are the same table only). 

## 3) Joining Multiple Tables:
```sql
SELECT f.title, l1.name, l2.name
FROM film f
JOIN language l1
ON f.language_id = l1.language_id
JOIN language l2
ON f.original_language_id = l2.language_id;
```
- We just combine two tables and then join the third table with the combined table.

## 4) Order of execution:
Order of Execution of SQL query:
- `FROM` - The database gets the data from tables in `FROM` .
- `JOIN` - Depending on the type of `JOIN` used in the query and conditions specified 
   for joining the tables in the `ON` clause, the database engine matches rows from 
   the virtual table created in the `FROM` clause.
- `WHERE` - After the `JOIN` operation, the data is filtered based on the conditions 
   specified in the `WHERE` clause. Rows that do not meet the criteria are excluded.
- `GROUP BY` - If the query includes a `GROUP BY` clause, the rows are grouped based 
  on the specified columns and aggregate functions are applied to the groups created.
- `HAVING` - The HAVING clause filters the groups of rows based on the specified conditions
- `SELECT` - After grouping and filtering is done, the SELECT statement determines 
   which columns to include in the final result set.
- `DISTINCT` - Duplicate rows are removed
- `ORDER BY` - It allows you to sort the result set based on one or more columns, 
   either in ascending or descending order.
- `OFFSET` - The specified number of rows are skipped from the beginning of the result set.
- `LIMIT` - After skipping the rows, the `LIMIT` clause is applied to restrict the number of rows returned.
- This is the logical order, not necessarily the physical execution order. Query 
  optimizers (e.g., in PostgreSQL, MySQL) may rearrange operations internally for 
  performance, but the results must behave as if this order was followed.
- Because `WHERE` executes before `SELECT`, you cannot use a column alias defined in 
  `SELECT` inside a `WHERE` clause. However, you can use it in `ORDER BY`, since that 
  runs after `SELECT`.
- Note: The type of joins discussed here is also known as Inner Joins.

## 5) Compound Joins
A Compound Join is one where Join has multiple conditions on different columns.
```sql
SELECT f1.name, f2.name
FROM film f1
JOIN film f2
ON (f2.year BETWEEN f1.year - 2 AND f1.year + 2) AND f2.rental > f1.rental;
```
- Note:
   - Join does not need to happen on equality of columns always.
   - Join can also have multiple conditions.

## 6) INNER JOIN
- The keyword `INNER` is optional. By default, a join is `INNER` join.  As you see,
  an `INNER JOIN` doesn't include a row that didn't match the condition for any combination.
- If one of the column in the `ON` clause is `NULL` then that row won't be part of the 
  inner join. 

## 7) Left Join
Left join will include all rows from the left table in the `FROM` clause, and include 
rows from the right table after the `JOIN` clause, which matches join condition. 
If there is any row for which there is no match on the right side, then it will be 
considered as `NULL`.
- General Syntax:
```sql
SELECT column_name(s)
FROM table1 LEFT JOIN table2
ON table1.column_name = table2.column_name;

-- It's same as:

SELECT column_name(s)
FROM table1 LEFT OUTER JOIN table2
ON table1.column_name = table2.column_name;
```

## 8) Right Join
Right join will include all rows from the right table, and include rows from left 
table which matches join condition. If there is any row for which there is no match 
on the left side, then it will be considered as Null value.
```sql
-- General Syntax:
SELECT column_name(s)
FROM table1 RIGHT JOIN table2
ON table1.column_name = table2.column_name;

-- It's same as:
SELECT column_name(s)
FROM table1 RIGHT OUTER JOIN table2
ON table1.column_name = table2.column_name;
```

## 9) Full Outer Join
Full Outer join will include all rows from the left table as well as the right table.
If there is any row for which there is no match on either of the sides, then it will 
be considered as NULL value.
```sql
-- General Syntax:
SELECT column_name(s)
FROM table1 FULL OUTER JOIN table2
ON table1.column_name = table2.column_name;
```
- `MySQL` doesn't support `FULL OUTER JOIN`, however other DBMS like PostreSQL, etc. 
   might support it. Although, `FULL OUTER JOIN` is the correct query as per 
  SQL language standard.

## 10) CROSS JOIN
Cross join is a special type of join that doesn't have any condition. It just combines
every row of the first table with every row of the second table. So, the resulting 
table has N*M rows given that the two tables have N and M rows.
```sql
SELECT *
FROM students s
CROSS JOIN batches b;
```
## 11) USING
To join 2 tables on a column that has the same name in both the tables. 
```sql
SELECT *
FROM students s
JOIN batches b
USING (batch_id);  -- ON s.batch_id = b.batch_id; it replaces this line
```
- Note: `USING` is a syntactical sugar used to write queries with ease.

## 12) NATURAL JOIN
If we want to join 2 tables on all the columns that have the same name, we can use 
`NATURAL JOIN`. 
```sql
SELECT *
FROM students s
NATURAL JOIN batches b;
```
## 13) IMPLICIT JOIN
Here, we don't use the `JOIN` keyword. Instead, we just write the table names and 
the condition. 
```sql
SELECT *
FROM students s, batches b;
-- Above query will work as cross joins behind the scenes.

-- implicit join with the condition:
SELECT *
FROM students s, batches b
WHERE s.batch_id = b.batch_id;
```
- So the comma syntax of the Implicit Join always pairs with `WHERE` and doesn't work
  with `ON` or `USING`. `ON` or `USING` only works with `JOIN` keyword.

## 14) Join with WHERE vs ON
With the `ON` condition, the size of intermediary table will be less than `n*m` because
rows not matching the condition are filtered during row stitching itself; `n`-> size of table1, `m`-> size of table2.
- Now, same result can be achieved with below query with `WHERE` clause:
```sql
SELECT *
FROM A, B
WHERE A.id = B.id;
```
- Here, `A CROSS JOIN B` happens first, thus the intermediary table, `ans` contains `n*m` rows. 
- The filtering (`WHERE` condition) happens after this intermediary table is formed.
- The size of the intermediary table, `ans` is always greater or equal, when using
  `WHERE` compared to using the `ON` condition. Therefore, joining with `ON` uses less 
  internal space.
- The number of iterations on `ans` is higher when using `WHERE` compared to using `ON`. 
  Therefore, joining with ON is more time efficient.
- In conclusion, 
   1. The `ON` condition is applied during the creation of the intermediary table, 
      resulting in lower memory usage and better performance.
   2. The `WHERE` condition is applied after the creation of the
      intermediary table, requiring additional memory and resulting in slower performance.
   3. Unless you want to create all possible pairs, avoid using `CROSS JOINS`.

## 15) UNION:
`UNION` allows you to combine the output of multiple queries one after the other.
i.e. The SQL UNION is used to combine two or more SELECT statements.
```sql
SELECT name FROM students
UNION
SELECT name FROM employees
UNION
SELECT name FROM investors;
```
Now, as the output is added one after the other, there is a constraint: Each of these
individual queries should output the same number of columns.
- Note that, you can't use `ORDER BY` for the combined result because each of these 
  queries are executed independently.
- UNION outputs distinct values of the combined result. It stores the output of 
  individual queries in a `Set` and then outputs those values in final result. 
  Hence, we get distinct values. 
- But if we want to keep all the values, we can use `UNION ALL`. It stores the output
  of individual queries in a `list` and gives the output, so we get all the 
  duplicate values.
- UNION doesn't care about column names — it only cares about position (sequence) and
  data type. The final result set uses the column names from the first `SELECT` query.
- Always make sure corresponding columns are compatible with data type AND logical 
  meaning, not just dataType alone. 










