# Lecture | SQL 2: Keys

## CONTENTS:
1. Candidate Keys
2. Primary Key
3. Composite keys
4. Foreign Key

---

## I] **Candidate Keys**
### 1.1) **Definition:**

A **Candidate Key** is a *minimal* set of one or more columns (attributes) that 
uniquely identifies each row in a table. i.e. a minimal super key.

**In simplest terms:** It's a super key with no unnecessary columns. You cannot remove
any attribute from it without destroying its uniqueness.

Every Candidate key is a Super key but not every Super key is a Candidate key.

---

### 1.2) **Key Properties of a Candidate Key**

1. **Not-NULL:** Every attribute (column) that is part of a candidate key must 
    contain a value; it cannot be `NULL`. 
    This is often called the **entity integrity** rule.

---

### 1.3) **Examples**

**Example 1: Single-Column Candidate Key**

**Table:** `Students`

| student_id | email           | phone_number | first_name |
| :--------- | :-------------- | :----------- | :--------- |
| 101        | alice@mail.com  | 555-1234     | Alice      |
| 102        | bob@mail.com    | 555-5678     | Bob        |

*   **`student_id`** is a candidate key. It is unique and minimal (you can't remove 
    anything from a single column key).
*   **`email`** is also a candidate key. It is unique and minimal.

**Example 2: Composite Candidate Key (Multiple Columns)**

**Table:** `Enrollments` (Which students are in which classes?)

| student_id | course_id | enrollment_date |
| :--------- | :-------- | :-------------- |
| 101        | CSE101    | 2023-09-01      |
| 101        | MTH202    | 2023-09-01      |
| 102        | CSE101    | 2023-09-02      |

*   A single column like `student_id` is not unique (student 101 is there twice).
*   A single column like `course_id` is not unique (CSE101 is there twice).
*   **The combination `(student_id, course_id)` is a candidate key.** It is unique (a 
    student can't be enrolled in the same course twice) and minimal. If you remove 
    either column, the remaining one is not unique.

**Example 3: What is NOT a Candidate Key?**

Using the `Students` table 1 above:
*   **`(student_id, first_name)`** is a super key (it's unique), but it is **NOT** 
    a candidate key. It is *not minimal* because you can remove `first_name` and 
    `student_id` alone is still unique. The `first_name` is an unnecessary, redundant
    attribute.

-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------

## [II] Primary Key:
- Remember, for one table, we can have only one `Primary Key`.
- A Primary Key (PK) is the Candidate key chosen by the database designer to be 
  the main, unique identifier for each row in a table. 
- When we create a table in the database, then the database forces us to define a Primary key.
- It's a fundamental concept for creating relationships between tables, allowing 
  efficient data retrieval and management in relational databases.
- Identifier for Relationships:
  Primary keys are used to establish connections with other tables, forming foreign 
  key relationships, which are crucial for data integrity.
 
### 2.1) Properties of a Primary Key (PK):
1. **Non-null:** The primary key cannot contain a null (empty) value.
   This ensures every entity can be identified and sorted.
2. Database sorts the data by primary key.
   - If we choose 'email' as the primary key, then the sorting will take a lot of time as
     comparing the String is more complex than comparing integers. 
   - Also, for email, we will allot minimum 20 characters which is 20 byte as compared to 4 byte
     for integers. Thus, we prefer integer over the String.
   - This is why we often create a special integer column (like student_id) with the 
     AUTO_INCREMENT feature. This automatically adds a new, unique number for each 
     row and places new entries at the end of the table, avoiding the need for the 
     database to constantly sort data.
   - One student raised the doubt that if we choose phone number as the primary key, then
     we will not require to create an extra column of student_id as phone number is also
     an integer. But, if a phone number is chosen as primary key then as soon as a new entry
     is made to the table, then that entry will be sorted as per phone number and then 
     inserted at the appropriate sorted order. However, a student_id with AUTO_INCREMENT feature
     will automatically insert the new entry as the last row. Thus, sorting overhead will be gone.
3. Database outputs the result of every query sorted by primary key.
4. Database creates an index as well on primary key by default.
5. Creates Clustered Index (Implementation Detail):
*   In most RDBMSs (like MySQL/InnoDB, SQL Server), defining a primary key
    **automatically creates a clustered index** on that column(s). This physically
    orders the data on the disk according to the key, making lookups based on the
    PK extremely fast.

---

### 2.2) Qualities of a good Primary Key:
1. A good primary key should:
   - be fast to sort on.
   - have smaller size (to reduce the space required for behind the scene indexing).
   - not get changed.
2. If email id is chosen as the primary key, then user can change it in the future. 
   But, changing the primary key is not good for our database as the same primary key
   is used as foreign keys in some the other table. Thus, this will create issues.
3. Thus, ideally, we should not have user attributes as Primary key as they are prone to change.
4. Hence, it is suggested to have a single integer value with probably an AUTO_INCREMENT feature
   (explicitly defined by the database owner) as the primary key apart from the user 
   attributes. for ex: student_id, employee_id, course_id, sales_id, etc.

---

### 2.3) Types of Primary Keys:
#### (1) Composite Key:
A combination of two or more columns that, when taken together, uniquely identify 
each row.
#### (2) Surrogate Key:
An artificial, system-generated key (often a numerical ID) that has no inherent 
meaning but serves the sole purpose of uniquely identifying a record.
#### (3) Natural Key:
A primary key that uses an existing attribute from the table, such as a national 
identification number or a driver's license number, that is already unique by definition.

---

### 2.4) Choosing from Candidate Keys:
*   The primary key is **selected** from the pool of all available candidate keys
    for a table. The choice is based on factors like:
    *   **Stability:** Is the value static? (e.g., `user_id` is better than `email`,
        which a user might change).
    *   **Simplicity:** A single column is often preferred over a composite key.
    *   **Meaning:** Surrogate keys (meaningless integers) are often chosen over
        natural keys (like SSN) to avoid future issues.
---

### 2.5) **Example**

**Table:** `Students`

| student_id (PK) | email (UQ)      | phone_number | first_name |
| :-------------- | :-------------- | :----------- | :--------- |
| 101             | alice@mail.com  | 555-1234     | Alice      |
| 102             | bob@mail.com    | 555-5678     | Bob        |
| 103             | charlie@mail.com| 555-9012     | Charlie    |

*   **Candidate Keys:** Both `student_id` and `email` are candidate keys.
*   **Primary Key:** The designer chooses `student_id` as the Primary Key because:
    1.  It is stable (won't change).
    2.  It is simple (a single integer column).
    3.  It is a surrogate key with no built-in meaning, avoiding privacy and update issues.
*   **Note:** `email` can still be enforced as unique using a `UNIQUE` constraint, 
    but it is not the primary identifier.
   as the anchor point for relationships (foreign keys) with other tables.

----------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------

## [III] Composite keys:
- Composite Key: A Super key that uses more than one column to uniquely identify a row.
- Every composite key must, by definition, also be a super key. If it's not a super 
  key (not unique), it's not any kind of key at all.
- Note: Super, Candidate and Primary keys can be of both types - either a single key 
  or a composite key.
- Sir said that Composite keys are not used practically too much. 

----------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------

## [IV] Foreign Key:
- A foreign key in SQL is a column or a set of columns in one table that refers 
  to the primary key or a unique key in another table.
- Remember, we necessarily don't require primary key from the other table. We just require
  the key to be unique.
- It is used to form the relationship between two tables. This link is what makes 
  a database "relational". It's all about maintaining relationships and ensuring **data
  integrity**—meaning you can't have nonsense data, like an order for a customer
  that doesn't exist.
- **The Golden Rule of Foreign Keys: Data Consistency**
  The value in the foreign key column must always match a value that already exists
  in the primary key (or unique column) of the other table. It can also be `NULL` 
  if the design allows it, which means the relationship is optional.

### 4.1) Example: A Simple School Database

Let's imagine we have two tables: `Students` and `Courses`. A student can enroll 
in one course.

It establishes a link between two tables, creating a parent-child relationship. 
The table containing the foreign key is known as the "child table," and the table
containing the referenced primary or unique key is known as the "parent table."

#### 1. The `Courses` Table (The "Parent" Table)
This table has a primary key (`course_id`) that the other table will reference.

| course_id (PRIMARY KEY) | course_name  |
| :---------------------- | :----------- |
| 101                     | Mathematics  |
| 102                     | Science      |
| 103                     | Literature   |

#### 2. The `Students` Table (The "Child" Table)
This table has a foreign key (`enrolled_course_id`) that points *to* the `course_id`
in the `Courses` table.

| student_id (PRIMARY KEY) | student_name | enrolled_course_id (FOREIGN KEY) |
| :----------------------- | :----------- | :------------------------------- |
| 1                        | Alice        | 101                              |
| 2                        | Bob          | 102                              |
| 3                        | Charlie      | 101                              |
| 4                        | Diana        | `NULL`                           |

**Let's see how the foreign key works here:**

*   Alice (`student_id 1`) is enrolled in `course_id 101`. Looking at the `Courses` 
    table, 101 is Mathematics. **This is valid.**
*   Bob is enrolled in Science (`102`). **Valid.**
*   Charlie is also enrolled in Mathematics (`101`). **Valid.** (A course can have
    many students, but a student is in one course—this is a "Many-to-One" relationship).
*   Diana is not enrolled in any course yet, so her `enrolled_course_id` is `NULL`. 
    **This is also valid** if the table design allows it.

### 4.2) What does the Foreign Key *PREVENT*?

This is the most important part. The foreign key **enforces rules** and stops you 
from doing things that would break the logical links in your database.

**1. It prevents INVALID DATA.**
You **cannot** insert this student record:
`(5, 'Eve', 999)`
Why? Because `999` does not exist as a `course_id` in the `Courses` table. The 
database will reject this insert. This ensures every enrollment is for a real, 
existing course.

**2. It prevents ORPHANED RECORDS.**
You **cannot** delete the 'Mathematics' course (`course_id 101`) from the `Courses` 
table while Alice and Charlie are still enrolled in it.

If you did, their `enrolled_course_id` (101) would point to nothing. They would be 
"orphaned" records. The database will stop you from deleting it unless you first 
handle those students.

### 4.3) What can you do about it? The `ON DELETE` Clause

You can tell the foreign key what to do if someone tries to delete the primary key 
it points to. The main options are:

*   `ON DELETE RESTRICT` (The Default): **Say "No!"** Don't allow the delete if 
    anything is still referencing it. (This is what we described above).
    The default action, if nothing is specified, is always `RESTRICT`.
*   `ON DELETE CASCADE`: **Delete the children too.** If you delete the 'Mathematics' 
    course, it would automatically delete Alice and Charlie. (Use this very carefully!).
*   `ON DELETE SET NULL`: **Set the link to free.** If you delete the 'Mathematics' 
    course, it would set the `enrolled_course_id` for Alice and Charlie to `NULL`. 
    Diana's record would stay the same.
* The `ON DELETE SET DEFAULT` clause is part of the SQL standard for referential
  integrity. While `ON DELETE SET DEFAULT` is a valid concept in SQL, you must 
  remember that it is not available in MySQL.

   - Its intended behavior is:
      1.  **When a row in the parent table (the referenced table) is deleted...**
      2.  **...the foreign key column(s) in all matching child rows are set to a predefined DEFAULT value.**
      3.  This prevents the child rows from becoming "orphans" with a reference that points to nowhere. Instead, they are assigned a neutral, default value indicating the absence of a parent.

   - The Crucial Point: MySQL Does NOT Support It

   - **The most important thing to know is that the MySQL database engine (specifically 
     the InnoDB storage engine) does not support the `ON DELETE SET DEFAULT` or 
     `ON UPDATE SET DEFAULT` actions.**
   - If you try to use it in a `CREATE TABLE` or `ALTER TABLE` statement, MySQL will 
     raise an error.

*   NOTE:- The similar constraints can also be applied while updating the data.
*   The Technical Difference between `ON DELETE NO ACTION` and `ON DELETE RESTRICT`
    (According to the SQL Standard):
    -  `ON DELETE NO ACTION`: The constraint is checked after the statement is executed. 
        This means the database might temporarily be in a state where the foreign key is 
        violated during the operation, but it will roll back the entire operation if the 
        final state is invalid.
    -  `ON DELETE RESTRICT`: The constraint is checked during the statement execution. 
        The operation will be aborted the moment a violation is detected, without making any 
        changes.

----------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------

## V] Links:
1. UUID: https://www.cockroachlabs.com/blog/what-is-a-uuid/

---
