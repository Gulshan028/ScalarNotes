# Aggregate Queries & SubQueries and Views:

## 1) Aggregate Functions
- Aggregate Functions in SQL can be used to aggregate data. Aggregate functions will 
  always output 1 value i.e. the result of the aggregate function. You cannot print 
  the values of the rows.
- Aggregate functions only take not `NULL` values into account.
  So, if there are any null values in the column, they will not be counted.

## 2) COUNT
Count function takes the values from a particular column and returns the number of 
values in that `Set`. 
```sql
SELECT COUNT(batch_id) FROM students;  
-- o/p :- an integer value showing non-null entries in batch_id column of the students table

SELECT COUNT(batch_id), batch_id FROM students;
-- This will be an invalid query. Because, you are trying to print the values of 
-- batch_id column as well as the count of batch_id column. But, you can only print 
-- the count of batch_id column.
```
## 3) `*` (asterisk) to count number of rows in the table
We can do that by passing a `*` to the count function. You may think what if there is
a `NULL` value in a row. Yes, there can be one/more Null values in a row, but the 
whole row can't be `NULL` as per rule of MySQL.
```sql
SELECT COUNT(*) FROM students;
```
- Query optimizer converts `count(*)` into `count(1)` in order to optimize the query.
```sql
-- below query simply print 1 for 200 times given there are 200 rows in students table.
select 1 from students;
```
- `count(1)` will count all the rows of the table. Its result is  the same as count(*).
- Use `COUNT(*)` to count rows. It's the fastest, most standard, and clearest method,
  as it's optimized to count rows without reading data. Using `COUNT(column_name)` is
  less efficient because it must read and check each value for `NULL`s, even if that 
  column is a primary key.

## 4) Other aggregate functions
- Multiple aggregation functions in the same query as well. For example:
```sql
SELECT COUNT(batch_id), AVG(age) FROM students;
```
- Some aggregate functions are as follows.
   - `MAX`: Gives Maximum value
   - `MIN`: Gives minimum value
- Note that, values in the column must be comparable for `MAX` and `MIN`.
- `AVG`: Gives average of non-NULL values from the column. For example: 
   AVG(1, 2, 3, NULL) will be 2.
- `SUM`: Gives sum, ignoring the null values from the column.
```sql
SELECT MAX(age), MIN(age)
FROM students;

SELECT SUM(batch_id)
FROM students;

-- Very Important: We can't use Aggregates in Nested. It will give us error. 
SELECT SUM(COUNT(batch_id))
FROM STUDENTS;
-- This above query will not work.
```
- However, `distinct` can be used inside an aggregate function as `distinct` is not 
  an aggregate function.
```sql
SELECT SUM(DISTINCT(batch_id))
FROM STUDENTS;
```
## 5) GROUP BY clause:
`GROUP BY` clause allows us to break the table into multiple groups to be used by the
aggregate function.
- Example: `GROUP BY batch_id` will bring all rows with same batch_id together in one group, 
  on which aggregate functions will be applied.
- The result comes out to be a set of values where each value is derived from its corresponding group.
- Note that we can only use the columns in `SELECT` which are present in `GROUP BY` 
  because only those columns will have same value across all rows in a group.

### 5.1) Using two columns in a GROUP BY clause:
- Allows you to create groups based on the unique combinations of values from both columns.
```sql
SELECT Country, Year, SUM(Amount) AS TotalSales
FROM Sales
GROUP BY Country, Year -- Grouping by 2 columns!
ORDER BY Country, Year;
```
### 5.2) ORDER BY clause in a query that already has a GROUP BY:
After the GROUP BY clause is processed, the resulting dataset only contains:
- The columns you specified in the GROUP BY clause.
- The results of any aggregate functions (e.g., SUM(), COUNT(), AVG()).
- Any column that is functionally dependent on a GROUP BY column (this is a
  more advanced SQL concept).
- Therefore, your `ORDER BY` clause can only reliably reference columns or 
  expressions that exist in this new, grouped result set.

## 6) HAVING Clause:
HAVING clause is used to filter groups. 
```sql
SELECT COUNT(S.id), B.name
FROM Students S
JOIN Batches B ON S.batch_id = B.id
WHERE B.name = 'Computer Science'
GROUP BY B.name
HAVING COUNT(S.id) > 100;

-- **Placement rule** — `WHERE` always goes **after JOIN** and **before GROUP BY`, 
-- following the order of execution:
-- FROM → JOIN → WHERE → GROUP BY → HAVING → SELECT
```
- Using `WHERE` here instead of `HAVING` can give us error as`WHERE` is not build to 
  be able to handle aggregates as `WHERE` works with rows not groups.
- `WHERE` works with `SELECT`, `INSERT` and `DELETE` statements whereas `HAVING` works
   only with `SELECT` statements.

---

## 7) Subqueries:
- Subqueries is an intuitive way of writing SQL queries. It breaks a problem into 
  smaller problems and combines their result to get a complete answer.
- The main purpose of using the SubQuery is that it is more readable and easy to 
  understand compared to other logical and smart solutions.

```sql
SELECT * 
FROM students
WHERE psp > (SELECT psp 
             FROM students
             WHERE id = 18);
```
- Subqueries should always be enclosed in parentheses.
- The above query takes O(N^2). The subquery gets executed for every row. Hence, it 
  leads to bad performance. Although, SQL optimizers help with performance improvement.

## 8) Subqueries and IN clause:
- The subqueries can give 4 types of outputs: Single value, Single row, Single column, Table.
- We used >, <, =, <>, >=, <= operations for single value. Now, we will use `IN` clause 
  for more than one output.
- The `IN` clause in MySQL is designed to work with a single column of values. The 
  subquery returns one column with multiple rows, creating a list of values to check against.
```sql
SELECT DISTINCT name
FROM users U
WHERE U.is_student = true
    AND U.name IN (
        SELECT DISTINCT name
        FROM users U
        WHERE U.is_TA = true
    );
```
### 8.1) Subquery Returning a Table (Multiple Columns):
- The `IN` clause can work with subqueries that return more than one column, but only
  if the left-hand side of the `IN` operator is a "row constructor" (a tuple) with 
  the same number of elements.
```sql
-- 1. Scenario That Will FAIL ❌; ERROR: Operand should contain 1 column(s)
SELECT employee_id, first_name
FROM employees
WHERE department_id IN ( -- Single column on the left...
SELECT department_id, manager_id -- ...but two columns in the subquery. Mismatch!
FROM special_projects
);

/*
2. Scenario That Will WORK ✅
Option 1: Use a multi-column IN clause (The right way)
MySQL supports comparing a tuple (a pair or set of values in parentheses) against a 
subquery that returns the same number of columns.
 */
SELECT employee_id, first_name
FROM employees
WHERE (department_id, manager_id) IN ( -- Tuple of two columns on the left...
SELECT department_id, manager_id -- ...matches two columns in the subquery.
FROM special_projects
);
```

## 9) Subqueries in FROM clause:
```sql
SELECT * 
FROM students
WHERE psp > (
    SELECT max(psp)
    FROM (
        SELECT min(psp)
        FROM students
        GROUP BY batch_id
    ) minpsps
);
```
- Whenever you have a subquery in `FROM` clause, it is required to give it a name, 
  hence, `minpsps`.
- We can have subquery in `FROM` clause as well. This subquery's output is considered
  like a table in itself upon which you can write any other read queries.

## 10) `ALL` and `ANY`:
```sql
SELECT *
FROM students 
WHERE psp > ALL (  -- WHERE psp > ANY ( -> this is analogous to OR
    SELECT min(psp)
    FROM students
    GROUP BY batch_id
);
```
- `ALL` compares left hand side with every value of the right hand side. If all of 
  them return `True`, `ALL` will return `True`.
- lly, `ANY` compares the left hand side with every value on the right hand side. If
  any of them returns `True`, `ANY` returns `True`.

## 11) Correlated subqueries:
- Correlated subqueries are queries where the subquery uses a variable from the 
  parent query.
- For Example: Find all students whose psp is greater than the average psp of their 
  batch. Here, the subquery (avg psp of batch) is dependent upon which student we 
  are calculating it for as each student can have different batches.
```sql
SELECT *
FROM students S
WHERE psp > (
    SELECT avg(psp)
    FROM students
    WHERE batch_id = S.batch_id
);
```
- Here, subquery is using a variable `S.batch_id` from the parent query. Hence, this is
  the subquery.

## 12) `EXISTS`:
```sql
-- Query 1:
SELECT *
FROM students
WHERE id IN (
    SELECT st_id
    FROM tas
    WHERE st_id IS NOT NULL
);

-- Query 2: Using EXISTS
SELECT *
FROM students S
WHERE EXISTS (
    SELECT st_id
    FROM tas
    WHERE tas.st_id = S.id
);
```
- In Query 1, for each row in parent query, the subquery will be run and `students.id` 
  will be compared with all the `st_id`s which the inner subquery will return. 
  This will take more time.
- Whereas, the Query 2 gives the same result. However, it is much faster compared to 
  Query 1. This query is a correlated subquery. 
- What `EXISTS` does is, for every row of students it will run the subquery. If the 
  subquery returns any number of rows greater than zero, it returns `True`. In this 
  query, finding `tas.st_id = S.id` is faster because of indexes. And as soon as 
  MySQL finds one such row, `EXISTS` will return `True`. 
- `IN` clause saves the result of the subquery in memory and compares the outer row with
  this list. This is better when the outer table has larger rows and inner subquery 
  returns smaller datasets.
- `IN` clauses, modern databases like MySQL are highly optimized and do not perform 
  a naive, row-by-row scan of the entire list for every value they check. They use
  efficient data structures and algorithms (like hash sets and binary search) that 
  find matches in near-constant or logarithmic time, effectively "short-circuiting" 
  the need for a full scan. This is a key reason why IN with a large subquery can 
  still be performant.
- `EXISTS` is better when outer table has smaller number of rows and inner subquery
  has large number of rows, but outer item is fast checked due to indexed look up on
  subquery. 
- `EXISTS` becomes the superior choice when you can use an indexed column to correlate a large
  subquery table with a smaller outer result set.

## 13) Views:
- Databases allow for creation of views. Think of views as an alias which when 
  referred is replaced by the query you store with the view.
```sql
CREATE OR REPLACE view actor_film_name AS

SELECT
   concat(a.first_name, a.last_name) AS actor_name,
   f.title AS file_name
FROM actor a
  JOIN film_actor fa 
    ON fa.actor_id = a.actor_id 
  JOIN film f
    ON f.film_id = fa.film_id
```
- Note that a view is not a table. It runs the query on the go, and hence data 
  redundancy is not a problem.

### 13.1) Operating with views:
- Once a view is created, you can use it in queries like a table. Note that in the
  background the view is replaced by the query itself with view name as alias. 
```sql
SELECT film_name 
FROM actor_film_name      -- here, `actor_film_name` is the view, we created earlier.
WHERE actor_name = "JOE SWANK";
```
- With Views, it's super simple to write queries that we write frequently.
- An easy way to understand that is that assume every occurrence of `actor_file_name`
  is replaced by:
```sql
(SELECT
concat(a.first_name, a.last_name) AS actor_name,
f.title AS file_name
FROM actor a
JOIN film_actor fa
ON fa.actor_id = a.actor_id
JOIN film f
ON f.film_id = fa.film_id) AS actor_file_name

-- How to get all views in the database:
SHOW FULL TABLES WHERE table_type = 'VIEW';

-- Dropping a view
DROP VIEW actor_file_name;

-- Updating a view
ALTER view actor_film_name AS

SELECT
concat(a.first_name, a.last_name) AS actor_name,
f.title AS file_name
FROM actor a
JOIN film_actor fa
ON fa.actor_id = a.actor_id
JOIN film f
ON f.film_id = fa.film_id

-- See the original create statement for a view
SHOW CREATE TABLE actor_film_name
```
- Note: Not recommended to run update on views to update the data in the underlying 
  tables. Best practice to use views for reading information.

### 13.2) Materialised views:
Materialised views are views with a difference that the views also store results of 
the query. This means there is redundancy and can lead to inconsistency / performance
concerns with too many views. But it helps drastically improve the performance of 
queries using views. MySQL, for example, does not support materialised views. 
Materialised views are tricky and should not be created unless absolutely necessary
for performance.





