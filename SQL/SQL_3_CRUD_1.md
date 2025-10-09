# Lecture | SQL 3: CRUD - 1

## CONTENTS:
1. General info & SQL Queries
2. CREATE OPERATION using INSERT command
3. READ Operation using SELECT command
4. DDL vs DML
5. ENUM
6. Links

---

## I] General info & SQL Queries:
- Read Scalar GitHub notes first and then continue here.
- **SQL queries are CASE-SENSITIVE.** But it's better to keep SQL syntax in uppercase
  and table_names, column_names, etc in lowercase.
- When we set up sakila database into our MySQL, then we get two files. We have to
  execute both the files. The first file `sakila-schema` creates the tables into the 
  database and the second file `sakila-data` inserts the data into those tables.
- In MySQL Workbench, use -- for commenting a line.
----------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------

## II] CREATE OPERATION:
- Remember, this operation is not aimed at creating a new table. Its purpose is to create
  a new entry (a row) into the already existing table.
- We can add one row or multiple rows in just one query as per our requirement. 
- While inserting a new row, it is a good practice to specify column names in the query.
  Because, if we don't specify, then the row might be inserted, but we cannot be sure
  whether the values are inserted into the right columns.
- In this case, we also need to make sure that we provide values for each column in the table, in the
  order they are present in the table. As some columns like "id" column of primary key can have AUTO 
  INCREMENT constraint, for such column, we need to put `default` value. Some columns
  might have `NOT NULL` constraint and if we don't specify any value for them then 
  because of this constraint the query will result into error.
- For the remaining theory, visit sir's pdf and also scalar GitHub notes.
- Now, in our system, when we go to workbench, we might have multiple databases stored.
  Hence, we specify that use sakila database for further queries, with below query.
```sql
use sakila;
```
- Try as many `INSERT` queries as possible in MySQL Workbench.

-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------

## III] READ Operation using SELECT command:
- It is used to fetch the data from the database.
- See the pseudocode from the deepak sir pdf for how the SELECT query is processed
  at the SQL engine. I am explaining it here and also in 3.1) section below.
  - For `SELECT` query in SQL, the SQL engine creates an intermediary table.
    Initially, all the rows from the original table are added into this intermediary 
    table and then, one by one, as per the constraints, only the selective rows and 
    columns are added into the further intermediary tables which are formed in the due 
    process. In this way, a final result of the read query is shown to the user.
  
---

### 3.1) **How a SQL `SELECT` Query Works: The Intermediary Result Set**

**Core Concept:** When you execute a `SELECT` query, the SQL engine does not directly
read from the original table on disk to your screen. Instead, it creates a **transient,
in-memory intermediary result set** (a virtual table) that is built, modified, and 
finally sent to the user.

#### (1) **The Step-by-Step Process:**

1.  **Initialization & Full Scan:**
    *   The process begins with the `FROM` clause. The engine creates an initial 
        intermediary result set in its memory by loading **all the rows and columns** 
        from the table(s) you specified.
    *   Example: *For `SELECT * FROM film;`, the entire `film` table is loaded into this working
        area.*

2.  **Progressive Refinement (Applying Clauses):**
    *   The engine then processes each additional clause in your query, using the 
        *current* intermediary result as its input and producing a *new, refined* 
        intermediary result.
    *   **`WHERE` Clause:** Acts as a filter. The engine goes through the intermediary 
        table and **removes any rows** that do not meet the condition. The intermediary
        table gets smaller.
    *   **`GROUP BY` Clause:** Groups rows that have the same values and calculates 
        aggregates (like `COUNT`, `SUM`).
    *   **`HAVING` Clause:** Filters the grouped results, just like `WHERE` but for 
        aggregates.
    *   **`ORDER BY` Clause:** Sorts the entire intermediary result set based on the 
        specified column(s).
    *   **`SELECT` Clause:** Finally, if specific columns were requested (not `*`), it 
        now removes any extra columns from the intermediary result, leaving only the 
        ones you asked for.

3.  **Final Output:**
    *   After all clauses have been processed, the final state of the intermediary 
        result set is what is sent back and displayed to the user.

4.  **Cleanup:**
    *   Once the result is sent to the client, the engine discards the intermediary 
        result set from memory. The original data on the disk remains completely 
        unchanged.

**Key Takeaway:** The power of SQL lies in this **declarative** process. You simply 
declare *what* you want (the final result), and the engine figures out *how* to build 
it by creating and modifying these temporary intermediary results behind the scenes.

---

### 3.2) `DISTINCT` keyword:
The `DISTINCT` keyword applies to **the entire combination of all columns** you have 
selected in your `SELECT` statement. 

It eliminates the duplicates record from the output.

In your query:
```sql
SELECT DISTINCT release_year, rating FROM film;
```

The database will return all **unique combinations** of `release_year` AND `rating`.

**How it works:**
It looks at the values from both columns together for each row and only returns 
a row if that specific `(release_year, rating)` pair has not already been included 
in the result set.

---

#### (1) Example to Make it Clear

Imagine your `film` table has the following data:

| title | release_year | rating |
| :--- | :--- | :--- |
| Movie A | 2020 | PG |
| Movie B | 2020 | PG |
| Movie C | 2020 | R |
| Movie D | 2021 | PG |
| Movie E | 2021 | R |

Your query `SELECT DISTINCT release_year, rating FROM film;` would return:

| release_year | rating |
| :--- | :--- |
| 2020 | PG | // This combination appears only once
| 2020 | R |
| 2021 | PG |
| 2021 | R |

Even though there were two movies with `(2020, PG)` (Movie A and Movie B), 
the `DISTINCT` clause ensured that combination only appeared **once** in the final 
result.

**In summary: `DISTINCT` always works on the entire row of results, not on individual 
columns.** It removes duplicate rows where all the selected values are identical.

---

### 3.3) How `DISTINCT` clause will treat **all `NULL` values?

The `DISTINCT` clause will treat **all `NULL` values as equal**. Therefore, if a 
column has multiple `NULL` values, `DISTINCT` will collapse them into a **single
`NULL`** in the output.

#### (1) **Example:**

Imagine a `staff` table with a `manager_id` column that is nullable and has these 
values:

| manager_id |
| :--- |
| 1 |
| 3 |
| `NULL` |
| `NULL` |
| `NULL` |
| 3 |
| 1 |

Running this query:
```sql
SELECT DISTINCT manager_id FROM staff;
```

Will return **three rows**:

| manager_id |
| :--- |
| 1 |
| 3 |
| `NULL` |

The multiple `NULL` values are considered duplicates for the purpose of `DISTINCT`, 
so only one `NULL` is shown.


#### (2) Key Takeaway:

*   **`DISTINCT` keeps one `NULL`:** It does not remove `NULL`s entirely from the 
    result set; it includes a single `NULL` to represent all the `NULL` values in the
    column.
*   **This is standard behavior:** This is how `DISTINCT` works in all major SQL 
    databases (MySQL, PostgreSQL, SQL Server, etc.) as per the SQL standard.

#### (3) How to get only non-NULL distinct values?

If you want to see only the distinct **non-NULL** values, you need to explicitly
filter out the `NULL`s using a `WHERE` clause:

```sql
SELECT DISTINCT manager_id
FROM staff
WHERE manager_id IS NOT NULL;
```

**Result:**

| manager_id |
| :--- |
| 1 |
| 3 |

---

### 3.4) Select statement to print a constant value
```sql
SELECT title, "Hello World!" as Greeting 
from film;
```
The above query will print two columns. The second column will be named "Greeting" and
it will have all the entries as "Hello World!".

```sql
SELECT "I love my country!";
```
This query will  print just one row and one column with column name same as the String, 
also the cell will have same String.

---

### 3.5) Inserting Data from Another Table:
```sql
-- creating a different table
create table film_copy(
 film_name varchar(128) not null,
 release_year year default null);
 
 -- inserting data from one table into other table
 insert into film_copy(film_name, release_year)
 select title, release_year from film;
 
 -- reading new table:
 select * from film_copy;
```
Here, you can see that the names of the columns of the two tables can be different, but
they should be the same type of columns. I have checked above queries and they run successfully.

---

### 3.6) `WHERE` clause:
In pseudocode provided in scalar GitHub notes, we can see that the filtering of rows is done 
in the first step itself, while creating the first intermediary table. This makes the 
query efficient. 

-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------

## IV] DDL vs DML:

### 4.1) DDL (Data Definition Language)

DDL commands are used to define, change, or delete the structure of database objects
(tables, indexes, views, etc.). These commands automatically **commit** changes 
(save them to the database) immediately, and you cannot roll them back 
(in most standard SQL databases).

**Key DDL Commands:**

*   **`CREATE`**: Creates a new database object.
    ```sql
    CREATE TABLE Employees (
        ID INT PRIMARY KEY,
        Name VARCHAR(100),
        Hire_Date DATE
    );
    ```

*   **`ALTER`**: Modifies an existing database object (e.g., adds a column).
    ```sql
    ALTER TABLE Employees ADD Salary DECIMAL(10, 2);
    ```

*   **`DROP`**: Deletes an entire database object and its data.
    ```sql
    DROP TABLE Employees;
    ```

*   **`TRUNCATE`**: Deletes all records from a table but keeps the table structure. (It's often grouped with DML but is technically DDL because it cannot be rolled back in many systems).
    ```sql
    TRUNCATE TABLE Employees;
    ```

*   **`RENAME`**: Renames an existing object.
    ```sql
    RENAME TABLE Employees TO Staff;
    ```

**Characteristics of DDL:**
*   Affects the entire table/database structure.
*   Operations are auto-committed (permanent).
*   Usually used by database administrators and designers.

---

### 4.2) DML (Data Manipulation Language)

DML commands are used for managing data within existing database objects. 
These commands are **not** auto-committed; you can group them into transactions 
and choose to commit (save) or roll back (undo) them.

**Key DML Commands:**

*   **`SELECT`**: Retrieves data from one or more tables. (It's sometimes called a 
    DQL - Data Query Language - command but is universally included under DML).
    ```sql
    SELECT Name, Hire_Date FROM Employees;
    ```

*   **`INSERT`**: Adds new rows of data into a table.
    ```sql
    INSERT INTO Employees (ID, Name, Hire_Date)
    VALUES (1, 'Alice Smith', '2023-01-15');
    ```

*   **`UPDATE`**: Modifies existing data in a table.
    ```sql
    UPDATE Employees
    SET Salary = 75000
    WHERE ID = 1;
    ```

*   **`DELETE`**: Removes specific rows of data from a table.
    ```sql
    DELETE FROM Employees
    WHERE ID = 1;
    ```

*   **`MERGE`** (or `UPSERT`): Inserts or updates data depending on whether it already exists.

**Characteristics of DML:**
*   Affects the data inside tables (rows).
*   Operations are not auto-committed; can be rolled back.
*   Used by developers, data analysts, and end-users.
*   `CRUD` operations are DML commands.

---

### 4.3) Comparison Table: DDL vs. DML

| Feature | DDL (Data Definition Language)                  | DML (Data Manipulation Language)                |
| :--- |:------------------------------------------------|:------------------------------------------------|
| **Purpose** | Defines the **structure/schema**                | Manipulates the **data** within the structure   |
| **Commands** | `CREATE`, `ALTER`, `DROP`, `TRUNCATE`, `RENAME` | `SELECT`, `INSERT`, `UPDATE`, `DELETE`, `MERGE` |
| **Transaction Control** | **Auto-committed** (cannot be undo)             | **Not auto-committed** (can be undo)            |
| **Number of Rows** | Operates on the **whole table/database**        | Operates on **one or many rows**                |
| **Where it's used** | Database design and setup                       | Day-to-day operations and application use       |

-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------

## V] String literals in SQL standard:

In SQL standard, you **use single quotes (`'`) for string literals and double 
quotes (`"`) for identifiers.**

### 5.1) Single Quotes (`' ')` for String Literals (Values)

This is used to enclose actual text **values** (also called string literals) that 
you want to insert, update, or compare.

**Examples:**
```sql
-- Filtering with a string value in a WHERE clause
SELECT * FROM customers WHERE country = 'USA';

-- Inserting a new record with string values
INSERT INTO film (title, description) 
VALUES ('The Matrix', 'A computer hacker learns the truth about his reality.');

-- Updating a field with a string value
UPDATE employees SET first_name = 'John' WHERE id = 123;
```

**✅ Correct:** `WHERE name = 'Alice'`
**❌ Incorrect:** `WHERE name = "Alice"` (in most standard SQL databases)

---

### 5.2) Double Quotes (`" "`) for Identifiers (Database Object Names)

This is used to enclose **identifiers**—the names of database objects like tables, 
columns, aliases, etc. You primarily use them when an identifier:
*   Contains spaces or special characters
*   Is a **reserved keyword**
*   Needs to be case-sensitive (in databases that normally use case-insensitive 
    identifiers)

**Examples:**
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

**✅ Correct:** `SELECT "first name" FROM customers;`
**❌ Incorrect:** `SELECT 'first name' FROM customers;` (This would look for the
      *string literal* `'first name'`, not a column name).

For normal column names, we don't require double quotation marks. We only require in
special cases as mentioned above.

---

### 5.3) The Exception: MySQL

MySQL is a common exception to this rule. By default, MySQL allows the use of **both 
single quotes and double quotes for string literals**.

```sql
-- Both of these work in MySQL
SELECT * FROM users WHERE username = 'john_doe';
SELECT * FROM users WHERE username = "john_doe";
```

However, even in MySQL, it is considered **best practice to use single quotes for 
strings** to maintain consistency with the SQL standard and to avoid confusion when 
your code needs to run on other database systems (like PostgreSQL, SQL Server, etc.).

You can configure MySQL to behave in a standard way (treat double quotes only for 
identifiers) by setting the `ANSI_QUOTES` SQL mode.

---

### 5.4) Summary & Best Practice

| | **Standard SQL** | **MySQL (Default)** | **Best Practice** |
| :--- | :--- | :--- | :--- |
| **String Literals**<br/>(values) | **Single Quotes** `'string'` | Both `'string'` and `"string"` | **Use Single Quotes** `'string'` |
| **Identifiers**<br/>(table/column names) | **Double Quotes** `"name"` | Backticks `` `name` `` | Use the standard **Double Quotes** `"name"` or MySQL's `` `name` `` |

**Simple Rule of Thumb:**
*   **For text/data values: Always use `'single quotes'`.**
*   **For names of things (especially if they have spaces): Use `"double quotes"` 
    (or backticks in MySQL).**

This will make your SQL code portable, standard, and much less error-prone.

-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------

## VI] ENUM:
### 5.1) What is an Enum?

An **enum** (short for **enumeration**) is a special data type in Java that enables 
a variable to be a set of predefined constants. It is used to represent a fixed number
of possible values, making your code more readable, type-safe, and maintainable.

Think of it like a list of options. For example:
*   The days of the week: `MONDAY, TUESDAY, ... SUNDAY`
*   The states of an order: `PENDING, PROCESSING, SHIPPED, DELIVERED`
*   The cardinal directions: `NORTH, SOUTH, EAST, WEST`

```java
public enum OrderStatus {
        PENDING,        // These are the only
        PROCESSING,     // possible valid values
        SHIPPED         // for this type.
}
    
OrderStatus status = OrderStatus.SHIPPED;
```
- Now, in above code, on RHS, `OrderStatus` acts as a data-type and `status` acts as a 
  variable. So, `status` can hold any value out of 3 specified values only. Here, we 
  will say that `OrderStatus` is an enum.

---

### 5.2) Key Features and Benefits of Enums

1.  **Namespace:** All enum constants are implicitly `public`, `static`, and `final`. 
    They are accessed via the enum's name (e.g., `OrderStatus.PENDING`), which avoids 
    naming conflicts.

---

### 5.3) How to Define a Basic Enum

**Definition:**
You use the `enum` keyword instead of `class` or `interface`.

```java
public enum Day {
    SUNDAY,    // Notice the constants are
    MONDAY,    // in uppercase, following
    TUESDAY,   // Java naming conventions
    WEDNESDAY, // for constants.
    THURSDAY,
    FRIDAY,
    SATURDAY
}
```

### 5.4) Advanced Enums: Adding Fields and Methods

Enums in Java are powerful because they are essentially **classes**. This means they 
can have:

*   **Constructors**
*   **Instance fields (attributes)**
*   **Methods**

This allows you to attach additional data and behavior to each constant.

**Example: A Planet Enum with Mass and Radius**
```java
public enum Planet {
    // Enum constants calling the constructor
    MERCURY(3.303e+23, 2.4397e6),
    VENUS  (4.869e+24, 6.0518e6),
    EARTH  (5.976e+24, 6.37814e6),
    MARS   (6.421e+23, 3.3972e6);

    // Instance fields
    private final double mass;   // in kilograms
    private final double radius; // in meters

    // Constructor (always private by default - cannot be public)
    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
    }

    // Public methods to access the fields
    public double getMass()   { return mass; }
    public double getRadius() { return radius; }

    // A method that uses the fields to calculate surface gravity
    public double surfaceGravity() {
        final double G = 6.67300E-11; // gravitational constant
        return G * mass / (radius * radius);
    }
}
```

In summary, **Java enums are a robust and feature-rich way to define a fixed set of
related constants**, going far beyond simple integer replacements and significantly
improving code quality.

### 5.5) Are enum constants' String?

**No, absolutely not.** This is one of the biggest misconceptions about enums in Java.

The constants you declare (`MERCURY`, `VENUS`, `PENDING`, `SHIPPED`) are **not 
strings**. They are **instances (objects)** of the enum class itself.

Think of it this way: when you write `enum Day { MONDAY, TUESDAY }`, it's as if you
are secretly writing a special kind of class that can only have a fixed number of 
pre-defined objects.

---

#### (1) The Key Insight: Enums are Classes

The simplest enum:
```java
public enum Day { MONDAY, TUESDAY }
```

It Is conceptually similar to this (the Java compiler does something like this behind the scenes):
```java
// This is ILLUSTRATIVE. You cannot write this yourself.
public final class Day { // The class is marked 'final'

    // Pre-defined, public, static, final instances of the Day class
    public static final Day MONDAY = new Day();
    public static final Day TUESDAY = new Day();

    // Private constructor to prevent anyone from creating new instances
    private Day() {}
}
```

**What does this mean?**
*   `Day.MONDAY` is an **object** of type `Day`.
*   It is **not** the string `"MONDAY"`.

---

#### (2) Demonstration: The Difference is Critical

Let's see why this distinction matters.

**1. Using an Enum (Correct and Safe):**
```java
Day today = Day.MONDAY;

if (today == Day.MONDAY) { // This compares OBJECT REFERENCES, and it's safe.
    System.out.println("It's Monday!");
}

// You CANNOT do this. It's a compiler error.
// Day today = "MONDAY"; // ERROR: Incompatible types!
```
The variable `today` can **only** hold one of the valid `Day` objects. The compiler 
guarantees it.

---

#### (3) How do they *act* like Strings? (The `.name()` and `.toString()` methods)

This is where the confusion comes from. Every enum automatically has two methods 
that return a String:

1.  **`.name()`**: Returns the exact name of the enum constant as declared. **This 
    method is `final`** (you cannot override it).
    ```java
    String nameOfDay = Day.MONDAY.name(); // returns "MONDAY"
    ```

2.  **`.toString()`**: By default, it also returns the name of the constant. **But 
    you can *override* this method** to provide a more user-friendly string.
    ```java
    public enum Day {
        MONDAY, TUESDAY;

        // Override toString to provide a better description
        @Override
        public String toString() {
            // You can write custom logic here
            return "Day: " + this.name();
        }
    }

    System.out.println(Day.MONDAY.toString()); // Prints "Day: MONDAY"
    System.out.println(Day.MONDAY); // Also prints "Day: MONDAY" (println calls toString() automatically)
    ```

#### (4) Summary

| Feature | Explanation |
| :--- | :--- |
| **What are the constants?** | They are **objects** (instances of the enum class), **not strings**. |
| **Type Safety** | A variable of type `Day` can **only** hold `Day.MONDAY`, `Day.TUESDAY`, etc. It cannot hold a string or an integer. |
| **Getting the Name** | You can get the constant's name *as a String* by calling `.name()`. |
| **String Representation** | You can get a customizable string representation by overriding the `.toString()` method. |

So, while you often *work with* the string names of enum constants, the constants 
themselves are full-fledged objects, which is what makes enums so powerful and safe.

-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------

## VI] Links:
1. https://github.com/7sandeepsinha/SQL-Feb25

-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------