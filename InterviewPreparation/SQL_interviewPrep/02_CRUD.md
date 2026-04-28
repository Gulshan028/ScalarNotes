# CRUD and JOINS

## 1) CRUD Operations:
Primarily, on any entity stored in a table, there are 4 operations possible:
1. Create (or inserting a new entry)
2. Read (fetching some entries)
3. Update (updating information about an entry already stored)
4. Delete (deleting an entry)
- Remember, MySQL queries aren't case-sensitive, but it's a good practice to write 
SQL keywords in uppercase and table_names, column_names, etc in lowercase for better readability.
- In MySQL Workbench, use `--` for commenting a line.

## 2) CREATE:  
- creates one or more rows in just one query into the already existing table.
- this query doesn't create table.
```sql
-- we might have multiple databases stored. Hence, we specify that use sakila database for further queries, with below query.
use sakila;
    
-- INSERT query With column names:
INSERT INTO film (title, description, release_year, language_id, rental_duration, rental_rate) 
VALUES ('The Dark Knight', 'Batman fights the Joker', 2008, 1, 3, 4.99),
       ('The Dark Knight Rises', 'Batman fights Bane', 2012, 1, 3, 4.99);
-- INSERT query without column names:
INSERT INTO film
VALUES (default, 'The Dark Knight', 'Batman fights the Joker', 2008, 1, NULL, 3, 4.99, 152, 19.99, 'PG-13', 'Trailers', default);
-- here, we have to specify values for all the columns. Thus, we use `default` for `film_id` as it is auto-increment, similar logic applies for others.
```
- The column names are optional. If you don't specify the column names, then the 
  values will be inserted in the columns in the order in which they were defined at 
  the time of creating the table.
   - This is not a good practice, as it makes the query prone to errors as we cannot
     be sure whether the values are inserted into the right columns. So always specify
     the column names.
   - If you don't specify column names, then you have to specify values for all the 
     columns, including `film_id`, `original_language_id` and `last_update`, which
     we may want to keep `NULL` or `default`.
   - We use `default` for columns whose values are predefined, like AUTO_INCREMENT primary ids.

## 3) READ:
- How the `SELECT` query is processed at the SQL engine?
   - For `SELECT` query in SQL, the SQL engine creates an intermediary table. Initially,
     all the rows from the original table are added into this intermediary table.
     Then, one by one, as per the constraints and clauses like `WHERE`, `GROUP BY`, 
     `JOIN`, `HAVING`, `ORDER BY`, `LIMIT`, etc., only the selective rows and columns 
     are added into the further intermediary tables which are formed in the due 
     process. In this way, a final result of the read query is shown to the user.

### 3.1) SELECT statement:
- `SELECT` statement is used to read data from a table.`Select` command is similar 
   to print statements in other languages. 
- some examples of `SELECT` statement are:
```sql
-- The basic syntax of the `SELECT` statement is:
SELECT column1, column2, ...
FROM table_name
WHERE condition;

-- To select all columns from the `film` table:
SELECT *
FROM film;

-- To select specific columns:
SELECT title, release_year
FROM film;
```
- Also, the column names are case-insensitive, so `title` and `TITLE` are the same. For example, following query would have also given the same result:
```sql
SELECT TITLE, RELEASE_YEAR
FROM FILM;
```
### 3.2) Alias `as`:
- This keyword is used to rename a column or table with an alias. It is temporary 
  and only lasts until the duration of that particular query.
```sql
SELECT title as film_name, film_id as id
FROM film;
```
- `AS` is optional for table aliases
- `AS` is optional but recommended for column aliases for clarity

### 3.3) `DISTINCT` keyword:
- This is used to return only distinct (different) values. It eliminates duplicate 
  records from the result set and displays unique non-null values of column_1.
```sql
SELECT DISTINCT rating FROM film;

-- 'DISTINCT' keyword with multiple columns:
SELECT DISTINCT rating, release_year FROM film;

-- this below query gives an error as it wants to print all the ratings and only Distinct release_years, which isn't possible.
SELECT rating, DISTINCT release_year FROM film;
```
- Note: `DISTINCT` keyword must be before all the column names, as The `DISTINCT` keyword
  applies to the entire combination of all columns you have selected in your `SELECT` 
  statement.
- The multiple `NULL` values are considered duplicates for the purpose of DISTINCT, 
  so only one `NULL` is shown per query,in case of one column in the `SELECT`.

### 3.4) `SELECT` statement to print a constant value:
```sql
SELECT 'Hello World!' as greeting;

-- You can also combine it with other columns
SELECT title, 'Hello World' FROM film;
```
### 3.5) Operations on Columns:
```sql
SELECT title, ROUND(length/60) FROM film;
-- here, we performed a mathematical operation on the `length` column and rounded it to the nearest integer.
```
- `ROUND` function is used to round off a number to the nearest integer.

### 3.6) Inserting Data from Another Table:
- `SELECT` can also be used to insert data in a table. Let's say we want to insert 
   all the films from the `film` table into the `film_copy` table.
```sql
INSERT INTO film_copy (title, description)
SELECT title, description
FROM film;
```

### 3.7) `WHERE` clause:
- We can use the WHERE clause to filter rows based on a condition. Example:
```sql
SELECT * FROM film WHERE rating = 'PG-13';
```
- Note that the `WHERE` clause is always used after the `FROM` clause. 

### 3.8) AND, OR, NOT
- These are logical operators used to combine multiple conditions in the `WHERE` clause.
```sql
-- AND operator: returns row if both conditions are true
SELECT * FROM film WHERE rating = 'PG-13' AND release_year = 2006;

-- OR operator: returns row if at least one of the conditions is true
SELECT * FROM film WHERE rating = 'PG-13' OR release_year = 2006;

-- NOT operator: returns row if the condition is false
SELECT * FROM film WHERE NOT rating = 'PG-13';
```
- If you are using multiple operators, it is always a good idea to use parentheses to make your query more readable. Else, it can be difficult to understand the order 
  in which the operators will be evaluated. Example:
```sql
SELECT * FROM film WHERE rating = 'PG-13' OR (release_year = 2006 AND rental_rate = 0.99);
```
-  MySQL also supports other comparison operators like >, <, >=, <=, != etc. Just one special case, != can also be written as <> in MySQL. 
```sql
-- Example 1:
SELECT * FROM film WHERE release_year >= 2006 AND release_year <= 2010;

-- Example 2:
SELECT * FROM film WHERE rating <> 'PG-13';
```
### 3.9) `IN` operator:
With comparison operators, we can only compare a column with a single value. `IN` operator allows to compare a column with multiple values. It is a shorthand for multiple `OR` conditions.
```sql
-- below query is equivalent to `SELECT * FROM film WHERE rating = 'PG-13' OR rating = 'R';`
SELECT * FROM film WHERE rating IN ('PG-13', 'R');

-- `NOT IN` operator: returns row if the column value is not in the specified list of values. i.e. returns films that have ratings anything other than the below 2
SELECT * FROM film WHERE rating NOT IN ('PG-13', 'R');
```

### 3.10) ORDER BY Clause
`ORDER BY` clause allows to return values in a sorted order.
```sql
-- query returns all the rows from the film table in ascending order of the title column.
SELECT * FROM film ORDER BY title;

-- for descending order, we can use the DESC keyword.
SELECT * FROM film ORDER BY title DESC;

-- sort by multiple columns: rows are sorted based on first column in the ORDER BY clause and if there is a tie, then they are sorted based on the second column and so on.
SELECT * FROM film ORDER BY title, release_year;
SELECT * FROM film ORDER BY title DESC, release_year DESC;

-- You can ORDER BY on a column which is not present in the SELECT clause. Example:
SELECT title FROM film ORDER BY release_year;
```
- The `ORDER BY` clause is applied after the `WHERE` clause and only after that `SELECT` statement is executed. And that's why you can sort based on columns not even in the `SELECT` clause.

### 3.11) BETWEEN Operator
```sql
SELECT * FROM film WHERE release_year BETWEEN 2005 AND 2010;
```
- `BETWEEN` operator is inclusive of the values specified. So, the above query will return all the films which have a release year >= 2005 and <= 2010. So that is something to be mindful of.
- `BETWEEN` Operator also works for strings. Let's assume that there is a country table with a "name" column of type varchar. If we execute this query:
```sql
Select * from country where name between 'a' and 'b';
```
- The above query will give us all country names starting with A/a till B/b.
- The above query willl limit answers till letter b only. Ex: 'Bolivia' will not be included since it have more letters than just b.
- Therefore above query gives all countries between a till b. Regardless of case sensitivity.
- `BETWEEN` works with other data-types as well such as dates. 
```sql
SELECT * FROM Orders
WHERE OrderDate BETWEEN '2023-07-01' AND '2024-01-01';
```

### 3.12) LIKE Operator:
-  Whenever there is a column storing strings, there comes a requirement to do some 
   kind of pattern matching. We use `LIKE` operator for that. It is used in 
   the `WHERE` clause to search for a specified pattern in a column.
- The `LIKE` operator works with the help of 2 wildcards in our queries, `%` and `_`.
- The percentage `%` wildcard matches any number of characters (>= 0 occurrences of 
  any set of characters). 
- The underscore _` wildcard matches exactly one character (any character). 
```sql
SELECT * FROM film WHERE title LIKE '%LOVE%';

-- These pattern strings are case insensitive as well.
-- Hence below query will give same results as above.

SELECT * FROM film WHERE title LIKE '%LovE%';
```
#### (1) ESCAPE Clause:
When the string you're matching against contains `_` or `%` characters that should 
be treated as string literals and not as wildcards, you have several options:

1. ESCAPE Clause (Most Common)
   Use the ESCAPE clause to define an escape character:
```sql
-- Using backslash as escape character
SELECT * FROM products
WHERE description LIKE 'discount\_50\%%' ESCAPE '\';

-- Using any character as escape character
SELECT * FROM table_name
WHERE column_name LIKE '%#_search%' ESCAPE '#';
```
Most databases support the `ESCAPE` clause, so that's usually the best approach for 
cross-database compatibility. The backslash (\) is commonly used as the escape 
character, but you can use any character you prefer.

### 3.13) IS NULL and IS NOT NULL:
- We store empties, i.e. no value for a particular column of a particular row as`NULL`.
- Interestingly working with `NULL`s is a bit tricky. We cannot use the `=` operator
  to compare a column with `NULL`.
- NULL means that the object isn't assigned, only the variable is declared. 
```sql
-- below query won't return anything as Because `NULL` is not equal to `NULL`. 
-- Infact, `NULL` is not equal to anything.
SELECT * FROM film WHERE description = NULL;

-- To compare a column with `NULL` We use the `IS NULL` operator as below:
SELECT * FROM film WHERE description IS NULL;

-- lly, we can use the `IS NOT NULL` operator to find all the rows where a particular
-- column is not NULL. Example:
SELECT * FROM film WHERE description IS NOT NULL;

-- find customers with id not equal to 2.
SELECT * FROM customers WHERE id != 2;

-- The above query will not return the customer with id NULL. So, you will get the 
-- wrong answer. Instead, you should use the IS NOT NULL operator as shown below:
SELECT * FROM customers WHERE id IS NOT NULL AND id != 2;
```

### 3.14) `ORDER BY` Clause with `DISTINCT` keyword:
When employing the `DISTINCT` keyword in an SQL query, the `ORDER BY` clause is limited to sorting by columns explicitly specified in the `SELECT` clause. 
This restriction stems from the nature of DISTINCT, which is designed to eliminate duplicate records based on the selected columns.
```sql
-- incorrect query: returns errors
SELECT DISTINCT title FROM film ORDER BY release_year;
```
- Consider, 'War' released in Hollywood in 1970 and in Bollywood in 2025. Now, without explicitly telling the database, 
  which one to consider, it is in ambiguity. if it considers 1970's War, then it may come at the start of the list. 
  However, if it considers 2025's War, then it may be at the bottom of the list. 
- Thus, because of this ambiguity, database denies this operation and returns error. 
- By limiting the `ORDER BY` clause to columns present in the `SELECT` clause, 
  you provide a clear directive on how the results should be sorted. 
```sql
-- this is the correct query
SELECT DISTINCT title FROM film ORDER BY title;
```
- Here, the database engine sorts the distinct titles alphabetically by the title column, 
  avoiding any confusion or ambiguity in the sorting process. 

### 3.15) LIMIT Clause
- `LIMIT` clause allows us to limit the number of rows returned by a query.
```sql
SELECT * FROM film LIMIT 10;
```
- The above query will return only 10 rows from the film table. 
- If you want to return 10 rows starting from the 11th row, you can use the `OFFSET` keyword, as shown below. 
```sql
SELECT * FROM film LIMIT 10 OFFSET 10;
```
- You can also use the `OFFSET` keyword without the `LIMIT` keyword as shown below:
```sql
SELECT * FROM film OFFSET 10;
```
- The above query will return all the rows starting from the 11th row from the film table.
- `LIMIT` clause is applied at the end. Just before printing the results. 
- Thus, if your query contains `ORDER BY` clause, then `LIMIT` clause will be applied after the `ORDER BY` clause. 
```sql
SELECT * FROM film ORDER BY title LIMIT 10;
```

## 4) Update Operation:
- this is used to update rows in a table. 
```sql
-- The general syntax is as follows:
UPDATE table_name SET column_name = value WHERE conditions;

-- Example: The below query will update the release_year column of the row with id 1 in the film table to 2006. 
UPDATE film SET release_year = 2006 WHERE id = 1;

-- updating multiple columns at once. 
UPDATE film SET release_year = 2006, rating = 'PG' WHERE id = 1;
```
- Update query iterates through all the rows in the table and updates the rows that 
  match the conditions in the `where` clause. So, if you run an update query without 
  a `where` clause, then all the rows will be updated.
```sql
UPDATE film SET release_year = 2006;
-- By default MySQL works with Safe_Mode 'ON' which prevents us from doing this kind of operations.
```
- The above query will result in all the rows of table having release_year as 2006, which is not desired. So, be careful while running update queries.

## 5) Delete
```sql
-- general syntax:
DELETE FROM table_name WHERE conditions;

-- example
DELETE FROM film WHERE id = 1;
```
- The above query will delete the row with id 1 from the film table.
- Beware, If you don't specify a `where` clause, then all the rows from the table 
  will be deleted. 
```sql
DELETE FROM film;
-- By default, MySQL works with Safe_Mode 'ON' which prevents us from doing this kind of operations.
```

## 6) Delete vs Truncate vs Drop:

## 6.1) Truncate:
```sql
-- general syntax:
TRUNCATE tableName;

-- example:
TRUNCATE film;
```
- The above query will delete all the rows from the film table. `TRUNCATE` command 
  internally works by removing the complete table and then recreating it. So, it is 
  much faster than DELETE. 
- But it has a disadvantage. It cannot be rolled back, meaning you can't get back your data. 
- Note: It also resets the primary key ID. For example, if the highest ID in the 
  table before truncating was 10, then the next row inserted after truncating will 
  have an ID of 1. While this is not the case with `DELETE`.

### 6.2) Drop
```sql
DROP TABLE film;
```
The above query will delete the film table. The difference between DELETE and DROP 
is that DELETE is used to delete rows from a table and DROP is used to delete the 
entire table. So, if you run a DROP query, then the entire table will be deleted. 
All the rows and the table structure will be deleted. So, be careful while running 
a DROP query. Nothing will be left of the table after running a DROP query. You will 
have to recreate the table from scratch.

### 6.3) DELETE:
- It removes specified rows one-by-one from table based on a condition(may delete all 
rows if no condition is present in query but keeps table structure intact).
- It is slower than `TRUNCATE` since we delete values one by one for each rows.
- Doesn't reset the key. It means if there is an auto_increment key such as 
  student_id in students table and last student_id value is 1005 and we deleted 
  this entry using query:
```sql
DELETE FROM students WHERE student_id = 1005;
```
- Now, if we insert one more entry/row in students table then student_id for 
  this column will be 1006. Hence continuing with same sequence without resseting 
  the value.
- It can be rolled back. Means if we have deleted a value then we can get it back again.

### 6.4) TRUNCATE:
- Removes the complete table and then recreats it with same schema (columns).
- Faster than DELETE. Since Truncate doesn't delete values one by one, rather it 
  deletes the whole table at once by de-referencing it and then creates another 
  table with the same schema hence Truncate is faster.
- Resets the key. It means if there is an auto_increment key such as student_id in 
  students table and last student_id value is 1005, and we Truncated this whole 
  table, then in new table the fresh entry/row will start with student_id = 1.
- It can not be rolled back because the complete table is deleted as an intermediate 
  step meaning we can't get the same table back.

### 6.5) DROP:
- Removes complete table and the table structre as well.
- It can not be rolled back meaning that we can't get back our table or database.




