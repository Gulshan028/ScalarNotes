# Lecture | SQL 4: CRUD - 2

## CONTENTS:
1. LIKE Operator
2. IS NULL Operator

---

Read Scalar's GitHub notes first.
## I] LIKE Operator:
if the String itself contains _ or %, what to do in that case?
### 1.1) Answer:
When the string you're matching against contains `_` or `%` characters that should be 
treated literally (not as wildcards), you have several options:

#### 1. **ESCAPE Clause** (Most Common)
Use the `ESCAPE` clause to define an escape character:

```sql
-- Using backslash as escape character
SELECT * FROM table_name 
WHERE column_name LIKE '%\_search%' ESCAPE '\';

-- Using any character as escape character
SELECT * FROM table_name 
WHERE column_name LIKE '%#_search%' ESCAPE '#';
```

#### 2. **Common Escape Patterns**
```sql
-- Match "file_name.txt"
SELECT * FROM files 
WHERE filename LIKE 'file\_name%.txt' ESCAPE '\';

-- Match "discount_50%"
SELECT * FROM products 
WHERE description LIKE 'discount\_50\%%' ESCAPE '\';

-- Match strings starting with underscore
SELECT * FROM table_name 
WHERE column_name LIKE '\_%' ESCAPE '\';
```

**Most databases support the `ESCAPE` clause**, so that's usually the best approach 
for cross-database compatibility. The backslash (`\`) is commonly used as the escape 
character, but you can use any character you prefer.

### 1.2) Equality check:
```sql
SELECT title, description 
WHERE description = 'Cartoon Movie';
```
This query will return the row which has the exact description.

## II] IS NULL Operator:
- To check empty String, we just need to check as below:
```sql
SELECT * 
FROM address
where address2 = ' ';
-- This query will return all the entries where address2 is an empty String.
```
- Gulshan's understanding: Look, NULL means the object has not been specified yet.
  and empty String means, the String has an address in the memory, but it contains just 
  a space or no characters at all.
- Learn RegExp i.e. Regular Expression in free time.

---

## III] `ORDER BY` with and without `DISTINCT`:
✅ **SQL Query Execution: ORDER BY and DISTINCT**

The key difference lies in when the data is removed during the logical processing order. 
Assume the Original Table has 10 columns.

### **Case 1: Without DISTINCT (e.g., `SELECT col1, col2 ...`)**

The `SELECT` clause only defines the final output shape; it doesn't immediately 
discard data internally.

| **Stage**        | **What Happens**                         | **Columns in Intermediate Table**       |
| ---------------- | ---------------------------------------- | --------------------------------------- |
| **FROM/WHERE**   | Data is fetched and filtered.            | 10 columns                              |
| **SELECT**       | Output columns defined, expressions run. | 10 columns (internally still available) |
| **ORDER BY**     | Data is sorted.                          | 10 columns (can sort by any of them)    |
| **Final Output** | Result is displayed to user.             | 2 columns (e.g., col1, col2)            |

**Conclusion:**
`ORDER BY` can use hidden columns because the full dataset is still available 
internally when the sorting occurs.

### **Case 2: With DISTINCT (e.g., `SELECT DISTINCT col1, col2 ...`)**

The `DISTINCT` clause forces the database to finalize the unique set of rows before 
sorting, permanently discarding associated hidden data.

| **Stage**        | **What Happens**                | **Columns in Intermediate Table**              |
| ---------------- | ------------------------------- | ---------------------------------------------- |
| **FROM/WHERE**   | Data is fetched and filtered.   | 10 columns                                     |
| **SELECT**       | Output columns defined.         | 10 columns (internally)                        |
| **DISTINCT**     | Duplicates removed permanently. | Only 2 columns (hidden data is lost)           |
| **ORDER BY**     | Data is sorted.                 | Only 2 columns (cannot sort by hidden columns) |
| **Final Output** | Result is displayed to user.    | 2 columns (e.g., col1, col2)                   |

**Conclusion:**
`ORDER BY` cannot use hidden columns because they are permanently removed from the 
intermediate table by the `DISTINCT` operation which runs earlier in the process.

---
