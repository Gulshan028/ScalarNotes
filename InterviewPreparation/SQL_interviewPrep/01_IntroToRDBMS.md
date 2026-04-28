# Introduction to RDBMS, Keys:

## 1) Why Excel is not a good choice for data storage and analysis?
**1. Handling Large Volumes of Data**
Excel starts to slow down or crash beyond a few thousand rows. Databases can handle millions — even billions — of records efficiently without any performance issues. Excel also lacks indexing, which means it has to scan through all the data to find what you need, while databases use indexes to quickly locate data.

**2. Multiple Users at the Same Time**
In Excel, if two people edit the same file simultaneously, it leads to conflicts or data loss. Databases are built for multi-user access — multiple people can read and write data at the same time without affecting each other i.e., Concurrency is also built-in in the databases. 

**3. Data Security and Access Control**
Excel has very limited security — you can password-protect a file, but that's about it. In a database, you can control exactly *who* can see or edit *which* data, down to individual rows or columns. This is critical for sensitive business data. Security is no longer an issue as database offers role based access, automatic data backup, and several other security features

**4. Data Integrity and Accuracy**
Excel allows anyone to accidentally delete a formula, overwrite data, or enter incorrect values. Databases enforce strict rules — called *constraints* and its columns accept a specific data type only— that ensure only valid and consistent data gets stored. This keeps the data reliable and trustworthy. 

**5. Summary:**
Thus we can conclude that Excel is a great tool for small-scale data analysis and quick calculations, but it’s not designed production-level robust data storage, multi-user collaboration, or complex querying. For any serious data management needs, a relational database is an efficient way to store data.

## 2) Database vs DBMS:
**Database**: A database is a structured collection of (related or unrelated) data that is stored and accessed electronically.
**DBMS (Database Management System)** is a software system that acts as an interface between users and databases and allows to create and efficiently manage a database. 
A DBMS allows us to create, retrieve, update, and delete data (often called CRUD operations). It also allows to define rules to ensure data integrity, user administration, security, and concurrency.
- Database is analogous to `.docx` file whereas DBMS is analogous to Microsoft Word software that allows us to create, edit, and manage the document. Or, database : Videos as DBMS : VLC media player. The database physically sits on your storage/server, while DBMS is simply the tool on top of it.
- "A Database and DBMS are separate — but they are interdependent. A database cannot be created, read, or managed without a DBMS. This is exactly why DBMS exists."
- Whereas, SQL is a language used to interact with the DBMS. It allows us to write queries to perform operations on the database through the DBMS. So, SQL is like the language we use to communicate with the DBMS, which in turn manages the database.

## 3) SQL / Relational Database:   
- Relational Databases allow you to represent a database as a collection of multiple related tables. Each table represents a specific
  entity (like Customers, Orders, Products) and has a set of columns and rows. Each row represents a unique record and each column represents a field. It uses SQL for queries.
- The key feature of a relational database is that it allows us to establish relationships between different tables using keys (primary keys and foreign keys).

## 4) NoSQL / Non-relational databases:
Non-relational databases are those databases that don't follow the relational model. They don't store data in form of tables. Instead, they store data in form of documents, key-value pairs, graphs, etc.

## 5) Properties of RDBMS:
1. Each table of RDBMS stores information about an entity or a relationship between entities.
2. Every row is unique. This means that in a table, no 2 rows can have same values for all columns.
3. All the values present in a column hold the same data type.
4. Values are atomic; i.e. each column has an individual entry rather than a group of values, lists, Arrays or Sets. Thus, first_name instead of Full_name should be column names. This is also known as First Normal Form (1NF) in database normalization.
  - This ensures Accurate Retrieval: If Skills column has multiple values, then querying for a specific skill becomes difficult. 
  - Updating a part of the entry becomes difficult. Changing Java to JavaScript when skills cell has multiple values.
  - Avoid Unnecessary Storage: Separate table for CountryName and cityName can avoid storing country name multiple times for each city. This cannot be achieved if we have a single column for entire address.
  - To Enable Proper Sorting: Sorting based on first name or last name becomes difficult if we have a single column for full name.
5. The columns sequence is not guaranteed. Thus, always use column names while writing queries. While MySQL guarantees that the order of columns shall be same as defined at time 
   of creating table, it is not a part of SQL standard and hence not guaranteed by all other DBMS and relying on order can cause issues if in future a new column is added in between.
   Always write explicit column lists in your INSERT, SELECT, and other statements. It makes your code robust, self-documenting, and safe against future schema changes.
6. The rows sequence is not guaranteed.To get rows in a particular order, you should always use `ORDER BY` clause in your query. Having said that, MySQL does return rows in order of 
   their primary key, but again, don't rely on that as not guaranteed by SQL standard.
7. The name of every column is unique. This avoids any ambiguity while writing queries. 

## 6) Keys: 
- Keys are used to uniquely identify a row in a table.

### 6.1) Super Key:
- A super key is a combination of columns whose values can uniquely identify a row in a table. A Super key can have redundant/extra columns.

### 6.2) Candidate Key:
- A candidate key is a minimal super key from which no column can be removed and still has the property of uniquely identifying a row.
- Every attribute (column) that is part of a candidate key must contain a value; it cannot be `NULL`. This is often called the entity integrity rule.

### 6.3) Primary Key:
- A primary key is a candidate key that is chosen by the database designer to uniquely identify rows in a table. A table can have only one primary key. Primary keys cannot have NULL values.
- Only MySQL sorts the data by primary key (other RDBMS keeps the inserted order since they use heap-based storage). All RDBMS' create an index on primary key.
- We don't choose table data as Primary key because user data can be updated. Hence, we use separate new column as Primary key. 
- A good primary key should:- be fast to sort on; have smaller size (to reduce the space required for behind the scene indexing); not get changed as it references foreign keys too.

## 7) Composite Keys
- A composite key is a key with more than one column, when taken together, uniquely identify each row. Any key with multiple columns (a collection of columns) is a composite key.
- Note: Super, Candidate and Primary keys can be of both type - either a single key or a composite key.

## 8) Surrogate Key:
An artificial, system-generated key (often a numerical ID) that has no inherent meaning but serves the sole purpose of uniquely identifying a record.

## 9) Natural Key:
A primary key that uses an existing attribute from the table, such as a national identification number or a driver's license number, that is already unique by definition.

## 10) Foreign Keys:
- A foreign key is a column or a group of columns in a database table (the "child" table) that links data to a primary key or unique key in another table (the "parent" table). The Foreign key column must have the same number of columns if it's a composite key.
- It is used to form the relationship between two tables. This link is what makes a database "relational".
- It ensures referential integrity / Data Consistency i.e. The value in the foreign key column must always match a value that already exists in the primary key (or unique column) of the other table, or it will fail. It can also be `NULL` if the design allows it, which means the relationship is optional.
- MySQL allows you to set ON DELETE and ON UPDATE constraints when creating a foreign key to avoid orphaning and inconsistency. You can specify what should happen in case an update or a delete happens in the other table.
- You can set 4 values for ON DELETE and ON UPDATE, if the referenced data is deleted or updated (i.e. parent key record). They are:
1. CASCADE: All rows in the child table containing that foreign key are also deleted or updated.
2. SET NULL: The foreign key in all rows containing that foreign key is set to NULL. This assumes that the foreign key column is not set to NOT NULL.
3. NO ACTION: MySQL will not execute the delete or update operation for the parent table. This is the default action.
4. SET DEFAULT: The foreign key in all the referencing rows is set to its default values. While ON DELETE SET DEFAULT is a valid concept in SQL, you must remember that it is not available in MySQL.

## 11) Introduction to SQL
- SQL stands for Structured Query Language, used to interact with relational 
  databases. It allows you to create tables, fetch data from them, update data, 
  manage user permissions, etc.
- Why "Structured Query" because it allows to query over data arranged in a 
  structured way. Eg: In Relational databases, data is structured into tables.

## 12) Types of SQL Commands:

### 12.1) DDL (Data Definition Language):
- DDL commands are used to define, change, or delete the structure of database objects
  (tables, indexes, views, etc.). These commands automatically commit changes (save 
  them to the database) immediately, and you cannot roll them back (in most standard 
  SQL databases).

#### (1) Key DDL Commands:

- `CREATE`: Creates a new database object.
```sql
-- Syntax to create a table in SQL:
CREATE TABLE tableName (
-- Structure:- columnName1 dataType constraints,
    id INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(50) NOT NULL,
    Hire_Date DATE,
    psp DECIMAL check ( between 0.00 and 100.00 )
    FOREIGN KEY (batch_id) REFERENCES batches(batch_id) ON DELETE CASCADE ON UPDATE CASCADE
);
-- i.e. inside round brackets after tableName, just define columnName, its dataType
-- and any constraints you want to apply on that column.
```

- `ALTER`: Modifies an existing database object (e.g., adds a column).
```sql
ALTER TABLE Employees ADD Salary DECIMAL(10, 2);
```

- `DROP`: Deletes an entire database object and its data.
```sql
DROP TABLE Employees;
```

- `TRUNCATE`: Deletes all records from a table but keeps the table structure. (It's 
  often grouped with DML but is technically DDL because it cannot be rolled back in 
  many systems).
```sql
TRUNCATE TABLE Employees;
```

- `RENAME`: Renames an existing object.
```sql
RENAME TABLE Employees TO Staff;
```

#### (2) Characteristics of DDL:
- Affects the entire table/database structure. 
- Operations are auto-committed (permanent). 
- Usually used by database administrators and designers.


2. DML (Data Manipulation Language):
- DML commands are used for managing data within existing database objects. These 
  commands are not auto-committed; you can group them into transactions and choose 
  to commit (save) or roll back (undo) them.
- These commands are used by developers, data analysts, and end-users.

3. DCL (Data Control Language):
- DCL commands are used to control the access of data inside the database. These commands are used to grant (GRANT) or revoke (REVOKE) permissions to users on database objects like tables, views, etc.
- DCL commands are also auto-commit in nature, meaning that once executed, the changes are immediately reflected and saved in the database.

4. TCL (Transaction Control Language):
- TCL commands are used to manage transactions in a database. A transaction is a sequence of one or more SQL operations that are treated as a single unit of work.
- TCL commands include COMMIT (to save changes), ROLLBACK (to undo changes), and SAVEPOINT (to set a point within a transaction to which you can later roll back).
- TCL commands are crucial for ensuring data integrity and consistency, especially in multi-user environments where multiple transactions may be occurring simultaneously. They allow you to control the outcome of transactions and ensure that the database remains in a consistent state.
- TCL commands are not auto-commit by default, meaning that changes made within a transaction are not saved until a COMMIT command is issued. This allows for greater control over when changes are finalized in the database.

5. DQL (Data Query Language):
- DQL commands are used to query and retrieve data from a database. The most common DQL command is SELECT, which allows you to specify the columns and rows of data you want to retrieve from one or more tables.
- DQL commands are read-only and do not modify the data in the database. They are used to fetch data based on specific criteria, perform calculations, and aggregate data for analysis. DQL commands are essential for extracting meaningful insights from the data stored in a database.

## 13) MySQL Workbench:
It is a user interface (UI) based software application that provides a graphical interface for you to interact with your MySQL database instead of using command-line commands.

## 14)String literals in SQL standard:
In SQL standard, you use single quotes (') for string literals and double quotes (") 
for identifiers.

### 14.1) Single Quotes (' ') for String Literals (Values)
This is used to enclose actual text values (also called string literals) that you 
want to insert, update, or compare.

Examples:
```sql
-- Filtering with a string value in a WHERE clause
SELECT * FROM customers WHERE country = 'USA';

-- Inserting a new record with string values
INSERT INTO film (title, description)
VALUES ('The Matrix', 'A computer hacker learns the truth about his reality.');

-- Updating a field with a string value
UPDATE employees SET first_name = 'John' WHERE id = 123;
```
✅ Correct: WHERE name = 'Alice' 

❌ Incorrect: WHERE name = "Alice" (in most standard SQL databases)

### 14.2) Double Quotes (" ") for Identifiers (Database Object Names)
This is used to enclose identifiers—the names of database objects like tables, 
columns, aliases, etc. You primarily use them when an identifier:
- Contains spaces or special characters
- Is a reserved keyword
- Needs to be case-sensitive (in databases that normally use case-insensitive identifiers)

Examples:
```sql
-- Querying a column whose name contains a space
SELECT "customer name", "email address" FROM customers;

-- Using a reserved keyword (like "user") as a column name
SELECT id, "user", password FROM app_users;

-- Creating a table with a non-standard name
CREATE TABLE "2024 Sales Data" (
"region code" INT,
"total $" DECIMAL
);

-- Using double quotes for a case-sensitive alias
SELECT first_name AS "FirstName" FROM employees;
```
✅ Correct: SELECT "first name" FROM customers; 

❌ Incorrect: SELECT 'first name' FROM customers; (This would look for the string 
   literal 'first name', not a column name).

For normal column names, we don't require double quotation marks. We only require 
in special cases as mentioned above.

### 14.3) The Exception: MySQL
MySQL is a common exception to this rule. By default, MySQL allows the use of both 
single quotes and double quotes for string literals.
```sql
-- Both of these work in MySQL
SELECT * FROM users WHERE username = 'john_doe';
SELECT * FROM users WHERE username = "john_doe";
```
However, even in MySQL, it is considered best practice to use single quotes for 
strings to maintain consistency with the SQL standard and to avoid confusion when 
your code needs to run on other database systems (like PostgreSQL, SQL Server, etc.).

You can configure MySQL to behave in a standard way (treat double quotes only for 
identifiers) by setting the `ANSI_QUOTES SQL` mode.

Simple Rule of Thumb:
- For text/data values: Always use 'single quotes'.
- For names of things (especially if they have spaces): Use "double quotes" (or `backticks` in MySQL).

This will make your SQL code portable, standard, and much less error-prone

## 15) What are ENUMS?
Refer your personal GitHub scaler notes in SQL section and go for `SQL_3_CRUD_1.md` notes.

