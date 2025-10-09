# Lecture | SQL 1: Intro to DBMS and Relational Model

## CONTENTS:
1. Introductory Definitions
   - Data
   - csv file
   - Database
   - DBMS (Database Management System)
   - SQL vs MySQL
   - MySQL Workbench
2. Type of Databases
   - Relational Databases
   - Non-Relational databases
3. Relational Databases
4. Properties of RDBMS
5. Keys
   - Super Key

---

## I] Introductory Definitions:

### 1.1) Data:
- In a database context, "data" refers to the raw, unorganized facts, figures, text, 
  images, or other information that is stored and managed within the database system.
- **Structured or Unstructured:**
  Data can be highly structured, such as in relational databases where it's organized 
  into tables with predefined columns and rows, or it can be unstructured, like text 
  documents, images, or videos in a NoSQL database.
- Prior to database, we would store data in files like Excel sheet, word document, notepad,
  `csv` file, etc.

---

### 1.2) `csv` file:
#### (1) Definition:
  A `CSV (Comma-Separated Values)` file is a plain text file used to store tabular data,
  where each line is a data record, and values within a record are separated by commas. 

---

#### (2) Example: Basic Student Roster
This is a simple file containing essential identification and contact information.

**Filename:** `students_basic.csv`
```csv
student_id,first_name,last_name,email,enrollment_date,psp,attendance
1001,Alice,Smith,alice.smith@university.edu,2023-09-05,98,89
1002,Bob,Johnson,bob.johnson@university.edu,2023-09-05,56,65
1003,Charlie,Williams,charlie.w@university.edu,2023-09-05,87,87
1004,Diana,Brown,diana.brown@university.edu,2022-09-07,90,90
1005,Eve,Davis,eve.davis@university.edu,2022-09-07,85,80
```
---

#### (3) Key Characteristics of a CSV File:

*   **Comma-Separated:** Values are delimited by commas (`,`).
*   **First Row is Header:** The first line typically contains the column names.
*   **Plain Text:** It is a simple text file that can be opened in any text editor (Notepad, TextEdit) or spreadsheet software (Excel, Google Sheets).
*   **Quoting Text:** If a value itself contains a comma (e.g., an address), it should be enclosed in double quotes (`"`) to prevent the comma from being interpreted as a new column.
    *   Example: `1006,John,Doephd,"123 Main St, Apt 4B",jdoe@email.edu`

These examples can be easily copied and pasted into a text editor and saved with a `.csv` extension to create functional files for practice.

---

### 1.3) Issues of storing data in files:
#### (1) Inefficient:
In the above `students_basic.csv` file, if I am supposed to find the average_psp or 
average_attendance. Then, I will need to iterate row by row and then find the required 
quantity. This makes the query and information finding very inefficient.

---

#### (2) Data Integrity issues:
There can be multiple instances of data integrity issues as follows:
1. In `enrollment_date` column, I am supposed to insert date. But, somehow, if I insert
   `String` data then I will not be warned and data will be saved.
2. While inserting the name as "Charlie", the user mistakenly inserts **Char, lie** then
   the name will be stored as "Char" and last_name will be stored as "lie". This will also
   cause the remaining data to be inserted in wrong columns.

---

#### (3) Concurrency not possible:
- We know that the files are present in Hard disk and data on it is read using a pointer.
  Now, at one instant, pointer can point to only one data, it cannot point to multiple locations
  on the hard disk. Hence, multiple users cannot access data from the file. i.e. Concurrency 
  is not possible with the file system.
- While the operating system allows multiple users to access a CSV file simultaneously 
  by providing separate logical pointers for each process, the file system itself offers
  no built-in, application-level concurrency control. 
- This means that if multiple users attempt to read from and write to the same CSV file
  at the same time, we face critical issues like lost updates (where one user's changes
  overwrite another's), dirty reads (reading inconsistent, intermediate data), and 
  general data corruption.
- Preventing this would require us, the application developers, to manually implement 
  a complex system of file locks and data validation checks from scratch. This approach
  is extremely inefficient, as locking an entire file for every small update creates 
  significant performance bottlenecks, and is highly error-prone.
- Therefore, for any multi-user application, achieving safe, reliable, and efficient 
  concurrency is not practically feasible with simple CSV files.

---

#### (4) Security issues:
- We know at enterprise level, role based access is given to the files. Say, for a software 
  document is there then sales people or product_managers would be able to only read the data
  and not write to it. However, software engineers can read as well as write the data.
- Say, if a file contains passwords of the employees or users and if anyone can read it.
  Then it is a breach of privacy for users as encryption is not easy with normal files.

---

### 1.4) Database:
- Just like, an Airbase is a place to store airplanes in the same way a database is a 
  place to store data.
- A database is an organized collection of structured information, typically stored 
  electronically, that can be easily accessed, managed, and updated. It's essentially 
  a structured way to store and retrieve data, often using a database management 
  system (DBMS). Think of it as a digital filing cabinet for information.
- Now, inefficiency is not an issue now as databases use complex data structures to
  store the data. Hence, retrieving data is not a cumbersome process.
- Data integrity issue will also be gone as in each column the data belongs to certain
  data-type. Say, phone number is of type `long` number then we cannot put String even 
  by mistake.
- Similarly, Concurrency is also built-in in the databases. Hence, that issue is also solved.
- And Security is no longer an issue as database offers role based access, automatic data 
  backup, and several other security features.
- Thus, we can say that database is an efficient way to store data.

---

### 1.5) DBMS:- Database Management System
- A DBMS (Database Management System) is a software program that acts as an interface 
  between users and databases, allowing them to store, manage, retrieve, and manipulate 
  data in a structured and efficient way.
- It provides features like data definition, 
  user administration, and security to ensure data is well-organized, consistent, and 
  easily accessible while maintaining data integrity and allowing for concurrent user 
  access.  

#### (1) Types of DBMS
DBMSs are broadly categorized into two main types:
- **SQL (Relational) DBMS:** Stores data in structured tables with rows and columns, using SQL for queries (e.g., Oracle, MySQL).  
-  **NoSQL (Non-Relational) DBMS:** Stores data in various formats like documents, key-value pairs, or graphs, designed for unstructured or semi-structured data. 

---

### 1.6) SQL vs MySQL:
#### (1) SQL (Structured Query Language):
- SQL is a standardized programming language designed for managing and querying data in 
  relational database management systems (RDBMS). 
- It is the language used to interact 
  with databases, allowing users to:
   - **Create and modify database schemas:** Define tables, columns, relationships, and constraints.
   - **Insert, update, and delete data:** Add new records, modify existing ones, and remove unwanted data.
   - **Retrieve data:** Select specific data based on various criteria, using clauses like SELECT, FROM, WHERE, JOIN, and ORDER BY.

---

#### (2) MySQL
- MySQL is an open-source relational database management system (RDBMS) that utilizes
  SQL as its primary language for data manipulation. 
- It is a software application that allows users to:
   - **Store and organize data:** Create and manage databases, tables, and their contents.
   - **Process SQL queries:** Interpret and execute SQL commands to perform operations on the stored data.
   - **Provide data access:** Allow applications and users to connect and interact with the database.

---

#### (3) Key Differences

- **Nature:** SQL is a language, while MySQL is a database management system that uses SQL.
- **Scope:** SQL defines the syntax and commands for interacting with relational databases in general. 
  MySQL is a specific implementation of an RDBMS that adheres to the SQL standard.
- **Software vs. Language:** MySQL is a software product that needs to be installed 
  and run, whereas SQL is a language that is used within database systems like MySQL.
- In essence, you use SQL to communicate with and manage data within a MySQL database, 
  just as you use a programming language to write instructions for a computer program.

---

### 1.7) MySQL Workbench:
It is a user interface (UI) based software application that provides a graphical 
interface for you to interact with your MySQL database instead of using command-line commands.

----------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------

## II] Type of Databases:
There are major two types of databases: viz, (i) Relational database, (ii) Non-Relational databases.
### 2.1) Relational Databases:
At Scaler, we have a very structured data like every student has same attributes.
Similarly, each instructor has same attributes, each mentor has same attributes.
Thus, storing data is very easy as data is structured and related. So, we can form multiple 
tables and relate them.

---

### 2.2) Non-Relational databases:
- If database is not relational, then the rest of all other databases are Non-Relational databases.
- Say, facebook is storing users' database. So, it is a Graph-based database (GraphQL) as
  any user can be friend of any other user, hence there is no well-defined pattern.
- Similarly, consider item list at Amazon. Each item has different attributes compared to other. 
  Say, a t-shirt has different attributes compared to a mobile phone.
- Thus, such data is then stored in Non-Relational database like MongoDB, Key-Value DB, 
  Columnar DB, GraphQL, etc.

----------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------

## III] Relational Databases:
- Relational Databases allow you to represent a database as a collection of multiple related tables.
- Each table is for an entity (like Student, Mentor, TA, Instructor, etc.) or for a relation of entities.
- All the values present in a column hold the same data type. So, data integrity won't arise.
- Every cell should only contain atomic value. It cannot contain Array, List or Map, etc.
  This is required to maintain the efficiency. 
   - Consider the example of array. It stores element at continuos memory locations.
     Say, an array of integers. So, at each index, we have an integer which takes a space
     4 byte. As array name references the 0th index thus now, I can easily access element(integer)
     at any index. Say, 0th index integer is stored at `100` memory location then 10 index
     integer will be stored at `100+10*4 = 140` location.
     This became possible because array stores atomic data. Each index contains a 4B integer
     or a fixed sized data-type element. Hence, O(1) time complexity for access is possible in arrays.
   - Similarly, a 2D array is also an array of array. Thus, all the rows are stored at continuous 
     location. Thus, if `array[0][0]` is stored at say `100` memory location, then `array[4][5]` will be
     stored at `(100+4*10*4+5*4) = 280` memory location, considering the fact that each row contains 10 integers.
     Thus, integer at 4th row and 5th column will be stored at `280` memory location.
   - In the same way, in the database, the rows are stored continuously. Thus, if List and array, etc
     are allowed, then data retrieving data would be complex as `List` doesn't have fix size. 
   - This is not the only reason. There are multiple other reasons as well.
   - The explanation above uses how arrays work in RAM (e.g., in C++ or Java) as an 
     analogy for how databases store data on disk. This is a good teaching tool, 
     but the reality is more complex.
        - Database Storage is More Complex: Modern database storage engines don't just lay out 
        rows in a single, continuous block of memory like a C++ array. They use sophisticated 
        structures like pages (fixed-size blocks, e.g., 8KB), heaps, B+Trees, and indexes. 
        A row might span multiple pages. The database has to manage pointers and offsets 
        within these pages.

---

### 3.1) Why each cell is atomic in relational database:
The requirement that each cell (the intersection of a row and a column) should be 
atomic is a core principle of the **First Normal Form (1NF)** in relational database 
design. This principle is also often referred to as the requirement for **atomic values**.

Here’s a breakdown of why this is so critical:

#### 1. What Does "Atomic" Mean?

In this context, **atomic** means that a single cell contains exactly one, indivisible piece of data. It cannot be a list, a set, or a composite value that you would be tempted to break down further.

*   **Atomic (Good):** `first_name = "Alice"`, `city = "Paris"`, `age = 24`
*   **Not Atomic (Bad):** `full_name = "Alice Smith"`, `address = "123 Main St, Paris, France"`, `phone_numbers = "555-1234, 555-5678"`

---

#### 2. Why Enforce Atomicity? The Key Reasons:

##### **a) To Ensure Accurate Retrieval and Manipulation of Data (The Primary Reason)**

This is the most important reason. If a cell contains multiple values, you cannot easily or reliably work with the data.

*   **Problem with Non-Atomic Data:** Imagine a `skills` column with the value `"Python, SQL, Java"`.
    *   **Query:** How do you find everyone who knows "SQL"? You would have to use a string operation like `WHERE skills LIKE '%SQL%'`. This is inefficient and error-prone—it would also match a skill like "Microsoft SQLServer" and might miss "SQL, " if the formatting isn't perfect.
*   **Solution with Atomic Data:** A separate related table (`EmployeeSkills`) with one skill per row.
    *   **Query:** The query becomes simple, fast, and accurate: `WHERE skill = 'SQL'`.

---

##### **b) To Eliminate Update Anomalies**

If you store multiple values in one cell, updating one part of that data becomes messy and risky.

*   **Problem:** In the `skills` example, changing "Java" to "JavaScript" for a user requires parsing the string, finding the correct part, and replacing it without messing up the commas and spaces. This is a complex operation for a simple update and极易出错 (very prone to errors).
*   **Solution:** With a separate table, you simply `UPDATE` or `DELETE` a single row. It's clean, simple, and unambiguous.

---

##### **c) To Avoid Unnecessary Storage and Processing Overhead**

*   **Problem:** Storing composite data often means repeating large chunks of information. For example, storing a full address in one cell (`"123 Main St, Suite 200, New York, NY, 10001"`) means the city and state are repeated for every person who lives in New York. This wastes storage space.
*   **Solution:** Atomicity leads to normalization. You would have a separate `Cities` table with a unique ID. The `Users` table would then just store a `city_id` (a small integer), dramatically reducing redundancy.

---

##### **d) To Maintain Data Integrity and Consistency**

*   **Problem:** With a non-atomic `phone_numbers` column, there is no way to enforce a consistent format. One entry might be `"555-1234, 555-5678"`, another `"555.1234 | 555.9876"`, and a third `"(555) 123-4567 (work)"`. This inconsistency makes the data nearly useless for any automated processing (e.g., an automated calling system).
*   **Solution:** An atomic `phone_number` column in a separate `Phones` table can have strict data type and format rules applied to it (e.g., a CHECK constraint to validate the format), ensuring all data entered is consistent.

---

##### **e) To Enable Proper Sorting and Filtering**

*   **Problem:** How do you sort a list of people by their last name if the name is stored in a single `full_name` column? You can't. You would have to write a complex function to split the string on the space and hope that every entry follows the "First Last" format.
*   **Solution:** With atomic `first_name` and `last_name` columns, sorting by `last_name` is trivial and built-in.

---

#### 3. Analogy: Mailing Addresses

Think of an address on an envelope:

*   **Non-Atomic (The Real World):** The address is a single block of text: "123 Main St, Apartment 4B, New York, NY 10001". This is great for a human mail carrier.
*   **Atomic (The Database):** The database breaks this into atomic parts:
    *   `street_number`: 123
    *   `street_name`: Main St
    *   `apt_unit`: Apartment 4B
    *   `city`: New York
    *   `state`: NY
    *   `zip_code`: 10001

This atomic structure allows the post office to **sort by zip code** (extremely efficient), a marketing company to **filter by city** to run a local campaign, and a delivery driver to **find the street number** easily.

---

#### 4. Summary: The "So What?"

Enforcing atomicity is not about being pedantic. It's the essential **first step in normalizing** your data. It transforms your database from a simple digital filing cabinet into a powerful, structured, and efficient tool that can:

1.  **Answer complex questions accurately.**
2.  **Ensure data remains consistent and corruption-free.**
3.  **Allow for efficient storage and processing.**
4.  **Facilitate future growth and changes to the application.**

By designing your tables with atomic values, you set a strong foundation for everything else a relational database is designed to do. Your instructor is emphasizing this point to steer you away from the bad habit of designing databases like you would design a single CSV file for a simple spreadsheet.

----------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------
## IV] Properties of RDBMS:
### 4.1) Property of RDBMS - 1
Relational Databases represent a database as a collection of tables with each table 
storing information about something. This something can be an entity or a relationship
between entities. 

Example: We may have a table called students to store information about students of a
batch (an entity). Similarly, we may have a table called student_batches to store 
information about which student is in which batch (a relationship between entities).

---

### 4.2) Property of RDBMS - 2:
Every row is unique. This means that in a table, no 2 rows can have the same values 
for all columns. 

Example: In the students table, no 2 students can have the same name, attendance and 
psp. There will be something different, for example, we might also want to store their
roll number to distinguish 2 students having the same name.

---

### 4.3) Property of RDBMS - 3:
All the values present in a column hold the same data type. 

Example: In the students table, the name column will have string values, attendance 
column will have integer values and psp column will have float values. 
It cannot happen that for some students psp is a String.

---

### 4.4) Property of RDBMS - 4:
Values are atomic. What does atomic mean? What does the word atom mean to you?
Correct. Similarly, atomic means indivisible. So, in a relational database, 
every value in a column is indivisible. 

Example: If we have to store multiple phone numbers for a student, we cannot store 
them in a single column as a list. How to store those, we will learn at the end of the
course when we do Schema Design. Having said that, there are some SQL databases that 
allow you to store list of values in a column. But that is not a part of SQL standard
and is not supported by all databases. Even those that support aren't most optimal 
with queries on such columns.

---

### 4.5) Property of RDBMS - 5:
The columns sequence is not guaranteed. This is very important. SQL standard doesn't
guarantee that the columns will be stored in the same sequence as you define them. 
So, if you have a table with 3 columns: name, attendance, psp, it is not guaranteed 
that the data will be stored in the same sequence. So it is recommended to not rely 
on the sequence of columns and always use column names while writing queries. 
While MySQL guarantees that the order of columns shall be the same as defined at the
time of creating table, it is not a part of SQL standard and hence not guaranteed by 
all databases, and relying on order can cause issues if in future a new column is 
added in between.

Of course. Let's break this down with a clear example.

#### (1) The Core Concept: A Table is an Unordered Set of Columns

Think of a database table not as a fixed, sequential list like an array 
(`[col1, col2, col3]`), but as an unordered *set* of columns, like a bag of items
`{name, attendance, psp}`.

The SQL standard says you must refer to items in the bag *by their name*, not 
by their position. While some database systems (like MySQL) may keep them in the
order you put them in the bag as a nice feature, the standard doesn't require it. 
Other systems are free to rearrange them for efficiency.

---

#### (2) Example: The Problem with Relying on Column Order

Let's imagine we have a simple table for tracking students.

**1. We create the table:**
```sql
CREATE TABLE students (
    name VARCHAR(50),
    attendance INTEGER,
    psp DECIMAL(3, 2)
);
```
In MySQL, this will *likely* store the data on disk in the order: `name`, 
then `attendance`, then `psp`.

**2. The "Bad Practice": the Query relying on Order:**
You want to insert a new student. The **quick-and-dirty** way is to rely on the column 
order:
```sql
-- UNSAFE: Implicit column insertion
INSERT INTO students VALUES ('Alice', 95, 8.75);
```
This query works *only if* the columns are in the exact order `(name, attendance, psp)`. 
The database interprets this as:
*   The first value `'Alice'` goes into the first column (`name`).
*   The second value `95` goes into the second column (`attendance`).
*   The third value `8.75` goes into the third column (`psp`).

**3. The Disaster Scenario: A New Requirement**
Your product manager now says, "We need to add an `email` column for all students."

A developer alters the table to add the new column **in the middle**:
```sql
ALTER TABLE students ADD COLUMN email VARCHAR(100) AFTER name;
```
Now, the logical structure of the table has changed. The new order is:
1.  `name`
2.  `email`  **(NEW!)**
3.  `attendance` (which was formerly 2nd, now is 3rd)
4.  `psp` (which was formerly 3rd, now is 4th)

**4. The Same "Bad Practice" Query Now Fails Spectacularly**
You run the *exact same* insert command again for a new student, Bob:
```sql
INSERT INTO students VALUES ('Bob', 98, 7.80);
```
The database doesn't know your intention. It blindly follows the column order:
*   The first value `'Bob'` goes into the first column (`name`) → **Correct.**
*   The second value `98` goes into the *new second column* (`email`) → **DISASTER!** 
    You're trying to put the integer `98` into an `email` VARCHAR field. 
    This will cause a severe error and the query will fail.

Even worse, if the data types were compatible (e.g., if `email` was added at the end),
the query might *succeed* but insert completely wrong data, creating a "silent error" 
that is very hard to debug.
| name | email | attendance | psp  |
| :--- | :---- | :--------- | :--- |
| Alice| NULL  | 95         | 8.75 |
| Bob  | 98    | 7.80       | NULL | <-- Data is corrupt!

---

#### (3) The Safe and Recommended Practice: Always Name Columns

The solution is simple and foolproof: **explicitly name the columns you are working
with.**

**The Safe Insert Query (from the beginning):**
```sql
-- SAFE: Explicit column insertion
INSERT INTO students (name, attendance, psp)
VALUES ('Alice', 95, 8.75);
```

**After adding the `email` column, the same safe insert still works perfectly:**
```sql
-- This still works exactly as intended!
INSERT INTO students (name, attendance, psp)
VALUES ('Bob', 98, 7.80);
```
This query explicitly tells the database: "Put the value `'Bob'` into the `name` 
column, `98` into the `attendance` column, and `7.80` into the `psp` column." 
The database doesn't care what order those columns are physically stored in. 
It uses the names as a map. The `email` column will be gracefully filled with
`NULL` (or its default value).

| name | email | attendance | psp  |
| :--- | :---- | :--------- | :--- |
| Alice| NULL  | 95         | 8.75 |
| Bob  | NULL  | 98         | 7.80 | <-- Data is correct!

#### (4) Conclusion

*   **Relying on Column Order:** Is like telling a chef "Add the first ingredient 
    you see in the fridge." The result depends entirely on how the fridge is organized,
    which can change. It's fragile and error-prone.
*   **Using Column Names:** Is like giving the chef a specific recipe: "Add 100g of
    sugar." It's explicit, safe, and will work correctly no matter how the kitchen is
    rearranged.

**Always write explicit column lists** in your `INSERT`, `SELECT`, and other statements. 
It makes your code robust, self-documenting, and safe against future schema changes.

#### (5) Learning:
Always write queries with column names specified. Because the query you wrote today may 
not work in the future, as the order of columns in the table may change.
```sql
-- never write SELECT queries with "*" as below;
SELECT * from Students
where id = 50;
```
Try to understand that this query won't work the same in the future as  the table is not 
guaranteed to remain the same in the future. It definitely changes as per the requirement.
Thus, the above query may give different output in the future.

---

### 4.6) Property of RDBMS - 6:
The rows sequence is not guaranteed. Similar to columns, SQL doesn't guarantee the 
order in which rows shall be returned after any query. So, if you want to get rows 
in a particular order, you should always use ORDER BY clause in your query which we
will learn about in the next class. So when you write an SQL query, don't assume that
the first row will always be the same. The order of rows may change across multiple
runs of the same query. Having said that, MySQL does return rows in order of their 
primary key (we will learn about this later on), but again, don't rely on that as 
not guaranteed by SQL standard.

Look, it is very much understandable that some rows might have been added in between
and some rows might have been deleted. Thus, always write code accordingly.

---

### 4.7) Property of RDBMS - 7:
The name of every column is unique. This means that in a table, no 2 columns can have
the same name. 

Example: In the students table, we cannot have 2 columns with the same name. 
This is because if I have to write a query to get the name of a student, 
we will have to write SELECT name FROM students. Now if there are 2 columns with the 
same name, how will the database know which one to return? 
Hence, the name of every column is unique.

---

### 4.8) NOTE: Tip from Deepak Kasera sir:
In Computer Science, never ever `ASSUME` things, and always ask clarifying questions.
Even in your DSA, never assume that array will have only positive numbers. No, it's not
guaranteed. It can very well have negative numbers, 0 or empty array at all.
Thus, asking clarifying questions is a must. And, this habit should reflect in your code too.
For ex: as per the principles 5 and 6 above, we have seen that we should never assume and hence
code accordingly.

-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------

## [V] Keys:

### 5.1) **Super Key - Definition**

A **Super Key** is a set of one or more columns (attributes) in a table that can be 
used to uniquely identify every row (tuple) in that table.

---

### 5.2) **Important Points:**

#### (1) Practical Example:-

Consider a `Users` table:

| user_id | email           | phone_number | first_name |
| :------ | :-------------- | :----------- | :--------- |
| 101     | alice@mail.com  | 555-1234     | Alice      |
| 102     | bob@mail.com    | 555-5678     | Bob        |
| 103     | charlie@mail.com| 555-1234     | Charlie    |

*   **Super Keys** (Some examples):
    *   `user_id` (Minimal - it's a Candidate Key)
    *   `email` (Minimal - it's also a Candidate Key)
    *   `(user_id, email)` (Non-minimal, includes an extra unique column)
    *   `(email, first_name)` (Non-minimal, but still unique)
    *   `(phone_number, first_name)` (This is a super key only if this combination 
         is unique. In this data, it is: (555-1234, Alice) is unique from 
         (555-1234, Charlie)).
    *   All columns combined: `(user_id, email, phone_number, first_name)` 
        (The trivial super key)

*   **What is NOT a Super Key?**
    *   `phone_number` alone (Not unique, Bob and Alice share the same number in this example).
    *   `first_name` alone (Not unique, many people can have the same name).

----------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------



