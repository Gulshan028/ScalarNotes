# SQL 7: Aggregate Queries

## I] Aggregate Functions:

- Only counts non-null values.
- Query optimizer converts count(*) into count(1) in order to optimise the query.
- Now, consider a query,
```sql
--Query 1
select 1 from students;

-- Query 2
select name, 1 from students;
```
Say, if students table has 200 rows, then above query-1 will simply print 1 for 200 times.
lly, the second query will print 2 columns viz. names and 1 for 200 times.

Thus, now we can easily understand that once we write aggregate function `count(1)` or 
`count("Hello!")` then it will count all the rows of the table. Its result is same as 
`count(*)`, which also gives total number of rows of the table.
- Student:- Why `count(*)` is used to count all the number of rows of the table and not
            some primary key column like count(student_key)?
   - Answer: Use `COUNT(*)` to count rows. It's the fastest, most standard, and 
     clearest method, as it's optimized to count rows without reading data. 
     Using `COUNT(column_name)` is less efficient because it must read and check each 
     value for NULLs, even if that column is a primary key.

---

### 1.1) Example of using two columns in a `GROUP BY` clause:
- It allows you to create groups based on the unique combinations of values from both columns.

    It's like asking a question that has two dimensions. For example, instead of just 
    "What is the total sales per country?" (one column), you ask "What is the total sales 
    per country **per year**?" (two columns).

#### (1) Example Scenario

Let's imagine a table named `Sales`:
    
| OrderID | Country   | Year | Amount |
    | :------ | :-------- | :--- | :----- |
    | 101     | USA       | 2023 | 150.00 |
    | 102     | USA       | 2023 | 200.00 |
    | 103     | Canada    | 2023 | 100.00 |
    | 104     | USA       | 2024 | 300.00 |
    | 105     | Canada    | 2024 | 250.00 |
    | 106     | Canada    | 2024 | 120.00 |

#### (2) Query with 2 Columns in GROUP BY:

We want to find the total sales amount for each combination of country and year.

```sql
SELECT
    Country,
    Year,
    SUM(Amount) AS TotalSales
FROM
    Sales
GROUP BY
    Country, Year -- Grouping by 2 columns!
ORDER BY
    Country, Year;
```

#### (3) Result

The query would output a result set where each row represents a unique group defined 
by a `(Country, Year)` pair.

| Country | Year | TotalSales |
| :------ | :--- | :--------- |
| Canada  | 2023 | 100.00     |
| Canada  | 2024 | 370.00     | -- (250.00 + 120.00)
| USA     | 2023 | 350.00     | -- (150.00 + 200.00)
| USA     | 2024 | 300.00     |

#### (4) How it Works:

1.  **Grouping:** The database engine first creates temporary groups in memory.
    *   All rows where `Country='Canada'` **AND** `Year=2023` go into one group.
    *   All rows where `Country='Canada'` **AND** `Year=2024` go into another.
    *   All rows where `Country='USA'` **AND** `Year=2023` go into another.
    *   And so on...

2.  **Aggregating:** After the groups are formed, the aggregate function (`SUM(Amount)` in this case) is calculated for each individual group.

---

### 1.2) `ORDER BY` clause in a query that already has a `GROUP BY`:

When you use an `ORDER BY` clause in a query that already has a `GROUP BY`, **there 
are specific rules and best practices for what you can order by.** You cannot order
by just *any* column from the original table.

#### (1) The Golden Rule

After the `GROUP BY` clause is processed, the resulting dataset only contains:
1.  The columns you specified in the `GROUP BY` clause.
2.  The results of any aggregate functions (e.g., `SUM()`, `COUNT()`, `AVG()`).
3.  Any column that is functionally dependent on a `GROUP BY` column (this is a more advanced SQL concept).

**Therefore, your `ORDER BY` clause can only reliably reference columns or expressions that exist in this new, grouped result set.**

---
 NOTE: Read Q.1 of Additional question and see its answer.