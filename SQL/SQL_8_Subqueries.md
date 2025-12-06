# Lecture | SQL 8: Subqueries and Views

## I] SubQuery:
1. The main purpose of using the SubQuery is that it is more readable and easy to understand
   compared to other logical and smart solutions.
2. The best way to approach the subquery problem is to first write separate queries for
   each of the subproblems and then write them together in the subquery pattern as a single
   query involving subqueries.

---

## II] `IN` clause:
1. Question: In MySQL, if a Subquery returns a row or a column or a table, then can 
   we use `IN` clause for all of these subqueries?
2. Answer: The `IN` clause in MySQL is designed to work with a **single column of values**. 
However, the subquery you use to provide those values can return different structures.
The key is understanding how MySQL handles each case.

### 1. Subquery Returning a Single Column (The Standard and Most Common Use)

This is the primary and intended use of the `IN` clause. The subquery returns one 
column with multiple rows, creating a list of values to check against.

**Example: Find all employees who work in a department located in London.**
```sql
SELECT employee_id, first_name, last_name
FROM employees
WHERE department_id IN (
    SELECT department_id
    FROM departments
    WHERE location = 'London'
);
```
*   **Subquery:** `(SELECT department_id FROM departments WHERE location = 'London')`
*   **Returns:** A single column (`department_id`) with one or more rows (e.g., `[101, 205, 307]`).
*   **How `IN` works:** It checks if the `employees.department_id` value exists in the list `[101, 205, 307]`.

**This is the ideal and most efficient scenario.**

---

### 2. Subquery Returning a Table (Multiple Columns) - 

#### (1) The Correct Rule:

The `IN` clause can work with subqueries that return **more than one column**,
but **only if the left-hand side of the `IN` operator is a "row constructor" (a tuple)
with the same number of elements.**

Let's contrast the two scenarios clearly:

#### 1. Scenario That Will FAIL ❌

This fails because the left side is a **single column** (`department_id`), but the
subquery is trying to return **two columns**. The single `department_id` value has
nothing to compare the second `manager_id` column to.

```sql
-- ERROR: Operand should contain 1 column(s)
SELECT employee_id, first_name
FROM employees
WHERE department_id IN ( -- Single column on the left...
    SELECT department_id, manager_id -- ...but two columns in the subquery. Mismatch!
    FROM special_projects
);
```

#### 2. Scenario That Will WORK ✅ 

**Option 1: Use a multi-column `IN` clause (The right way)**
- MySQL supports comparing a **tuple** (a pair or set of values in parentheses)
against a subquery that returns the same number of columns.
- This works because the left side `(department_id, manager_id)` is a **row constructor**
  with two elements, and the subquery returns **a result set with the same number of
  columns (two)** i.e. a list of rows with the same structure. 
  MySQL can now compare the entire row from the left with each entire row from the subquery.

```sql
-- SUCCESS: The number of elements on the left (2)
-- matches the number of columns returned by the subquery (2).
SELECT employee_id, first_name
FROM employees
WHERE (department_id, manager_id) IN ( -- Tuple of two columns on the left...
    SELECT department_id, manager_id -- ...matches two columns in the subquery.
    FROM special_projects
);
```

#### 3. Analogy

| Left Side of `IN` | Subquery Returns | Result | Explanation |
| :--- | :--- | :--- | :--- |
| `single_column` | **One** Column | **✅ WORKS** | Standard use case. |
| `single_column` | **Multiple** Columns | **❌ FAILS** | "Operand should contain 1 column" error. |
| `(col1, col2)` (Tuple) | **Multiple** Columns | **✅ WORKS** | Number of elements must match. This is the powerful row-wise comparison. |

---

## III] `IN` vs `EXISTS` :-

### 3.1) At a Glance: Key Differences

| Feature | `IN` | `EXISTS` |
| :--- | :--- | :--- |
| **Purpose** | Check if a value is in a **list**. | Check if a **row** is returned by a subquery. |
| **Subquery** | Typically executed **first**, and its **full result set** is loaded into a temporary structure. | Executed **for each row** of the outer query (a "correlated" subquery). |
| **NULL Handling** | The condition `value IN (list)` becomes `NULL` (unknown) if the list contains a `NULL` and the value isn't found. | Unaffected by `NULL` values in the subquery results. |
| **Performance** | Best when the subquery result is **very small**. | Best when the outer query result is **small** and the subquery table is **large and indexed**. |
| **Readability** | Very intuitive for checking against a static list of values. | Clearly expresses a dependency between the outer and inner query. |

---

### 3.2) Deep Dive with Examples

Let's use the classic `employees` and `departments` example.

**Logical Goal:** Find all employees who work in a valid department.

#### (1) Using `IN`

```sql
SELECT *
FROM employees e
WHERE e.department_id IN (
    SELECT d.department_id
    FROM departments d
);
```

**How it works:**
1.  The database **first executes the inner query**: `SELECT d.department_id FROM 
    departments d`. It gets a list of all valid department IDs (e.g., `[10, 20, 30]`).
2.  This entire list is stored in a temporary in-memory structure (like a hash set).
3.  Then, for each row in the `employees` table, it checks if the `department_id` 
    value is present in that temporary list.

**Performance:** This is efficient if the `departments` table is small. The list 
of IDs is tiny and the check for each employee is very fast (a hash lookup).

#### (2) Using `EXISTS`

```sql
SELECT *
FROM employees e
WHERE EXISTS (
    SELECT 1
    FROM departments d
    WHERE d.department_id = e.department_id -- Correlates with the outer query
);
```

**How it works:**
1.  For **each row** in the `employees` table, the database takes its `department_id` 
    value.
2.  It then executes the inner query (`SELECT 1 FROM departments d WHERE d.department_id = e.department_id`),
    using the value from the outer row.
3.  If the subquery finds **at least one matching row** (it doesn't matter what's 
    in the `SELECT` clause, hence `SELECT 1`), the `EXISTS` condition is true, and 
    the employee row is returned.

**Performance:** This is efficient if the `employees` table is small, or if the 
`departments.department_id` column is **indexed**. The database can perform a very 
fast indexed lookup for each outer row instead of scanning the entire `departments` 
table.

---

### 3.3) When to Use Which?

#### (1) Use `IN` when:
*   You are comparing against a **static, explicit list** of values 
    (e.g., `WHERE status IN ('Active', 'Pending')`).
*   The **subquery result set is very small** and the outer table is large.
*   The subquery is **non-correlated** (it doesn't reference the outer query) and can
    be computed once.

#### (2) Use `EXISTS` when:
*   The **outer table is small** and the subquery table is large.
*   The subquery table has **effective indexes** on the columns used to join it to 
    the outer query. The `EXISTS` clause can leverage these indexes for extremely 
    fast lookups.
*   You need to check for the existence of a row based on **multiple, complex 
    conditions** in the subquery.
*   You are concerned about `NULL` values in the result set of a subquery used with`IN`.

### 3.4) A Concrete Performance Example

Imagine the `departments` table has 1,000 rows and the `employees` table has 1,000,000 rows.

*   **`IN` approach:** The database first builds a list of 1,000 department IDs. 
    It then does 1,000,000 checks against this small, in-memory list. This is **very 
    efficient**.
*   **`EXISTS` approach:** For each of the 1,000,000 employees, the database must 
    probe the `departments` table to see if their ID exists. If 
    `departments.department_id` is indexed, each probe is a fast index lookup
    (log(n) complexity). 1,000,000 fast lookups might still be **slower** than 
     the `IN` scenario in this case.

Now reverse it: `departments` has 1,000,000 rows and `employees` has 1,000 rows.

*   **`IN` approach (DISASTER):** The database first tries to build a list of 
    1,000,000 department IDs. This consumes a huge amount of memory and time. Then 
    it does 1,000 checks. **This is very inefficient.**
*   **`EXISTS` approach (EFFICIENT):** For each of the 1,000 employees, it does one 
    fast indexed lookup in the massive `departments` table. This is **very efficient**.

### 3.5) Conclusion

*   **`IN`** is like downloading a full guest list (**the list**) to check people 
    at the door.
*   **`EXISTS`** is like asking a bouncer to run inside and check (**for each person**) 
    if their name is on the list in the book.

**Modern databases have very smart optimizers** that can sometimes rewrite an `IN` 
clause to use an `EXISTS`-like execution plan (a "semi-join") if it determines it
will be faster. However, understanding the fundamental difference is crucial for 
writing predictably high-performing SQL, especially with complex queries or large 
datasets. As a rule of thumb, if you are joining tables in your subquery, `EXISTS` is 
often the safer bet.

---

### 3.6) Gulshan's understanding:
- Look, in `IN` clause, there is a value or a tuple of columns on LHS of `IN` clause.
  However, in `EXISTS` clause, there is no such value on LHS of `EXISTS` clause.
- `EXISTS` clause simply checks if the current row has any correlation with the subquery
   using a correlated subquery and accordingly returns `true` or `false`.
- Question: say, `IN` clause is checking from a list or say a column then if it finds 
  the value under check, in the very first attempt, then does it stop the search on 
  remaining values in the list?

**Yes, in practice, the `IN` clause implementation is optimized to stop searching as 
soon as it finds a match.** This behavior is known as **short-circuit evaluation**.

#### (1) How Short-Circuiting Works with `IN`

There are two primary ways a database handles an `IN` clause with a subquery, 
and short-circuiting works in both:

##### 1. The "Hash Semi-Join" (Very Common for Large Lists)

This is the most efficient method and is likely used when the list of values from the subquery is large.

*   **What happens:** The database executes the subquery first and loads all the distinct values into an in-memory **hash set** (a data structure designed for lightning-fast lookups).
*   **The Check:** For each value from the main table (e.g., each `employee.department_id`), it computes a hash of the value and checks for its presence in the hash set.
*   **The "Short-Circuit":** This hash lookup is effectively a **constant-time operation**, denoted as O(1). It doesn't need to scan through the list. It calculates the hash and knows instantly whether the value exists in the set or not. This is the ultimate form of short-circuiting—the check is a single operation.

##### 2. The "Sorted List Scan" (Less Common)

*   **What happens:** The database executes the subquery first and sorts the list of results.
*   **The Check:** For each value from the main table, it performs a **binary search** on the sorted list.
*   **The "Short-Circuit":** A binary search doesn't scan the whole list. It repeatedly divides the list in half. For a list of 1,000,000 values, it finds a match (or confirms no match) in a maximum of ~20 comparisons. While not as instant as a hash lookup, it's still extremely fast and doesn't require a full scan.

##### 3. A Concrete Example

Imagine your subquery returns a list of IDs: `[101, 102, 103, 104, 105, ... up to 1000]`.

You are checking the value `103` from your main table.

*   **Hash Set:** The database calculates the hash for `103`. It looks at that exact "bucket" in the hash set. It sees `103` is there. **It returns TRUE immediately. It never looks at 104, 105, ... 1000.**
*   **Sorted List & Binary Search:**
   1.  It checks the middle value (e.g., `550`). `103 < 550`, so it discards the top half.
   2.  It checks the middle of the lower half (~`275`). `103 < 275`, discard again.
   3.  It continues narrowing down until it finds `103`. It might take ~10 steps instead of 1000.

---

#### (2) The Exception: The `NOT IN` Trap

This concept of short-circuiting is also why `NOT IN` can be dangerous and much slower if the list contains `NULL`.

*   `value IN (1, 2, NULL)` can return `TRUE` as soon as it finds a match (e.g., `value=1`).
*   `value NOT IN (1, 2, NULL)` **cannot short-circuit the same way.**
    To confirm a value is `NOT IN` the list, it must verify that the value is not equal to `1`, **and** not equal to `2`, **and** not equal to `NULL`.
    Since any comparison with `NULL` (e.g., `value = NULL`) yields `UNKNOWN`, the entire `NOT IN` expression evaluates to `UNKNOWN` (which is treated as `FALSE` in a `WHERE` clause) if a `NULL` is present in the list.
    The database might have to process the entire list to see if a `NULL` exists that would invalidate the result.

---

#### (3) Conclusion

Your intuition is correct. For `IN` clauses, modern databases like MySQL are highly 
optimized and **do not perform a naive, row-by-row scan of the entire list for every 
value they check.** They use efficient data structures and algorithms (like hash 
sets and binary search) that find matches in near-constant or logarithmic time, 
effectively "short-circuiting" the need for a full scan. This is a key reason why 
`IN` with a large subquery can still be performant.

---
#### (4) About `EXISTS` clause:
Of course. The `EXISTS` clause operates on a fundamentally different principle than 
the `IN` clause, and its searching behavior is key to its performance characteristics.

##### 1. The Core Principle: Short-Circuit on First Match

The `EXISTS` clause is designed to **check for the existence of *at least one* row** 
that satisfies the condition. Therefore, its search algorithm is brutally efficient:

**As soon as the database engine finds a single row in the subquery that matches the 
condition, it stops searching immediately and returns `TRUE`.**

It doesn't care about finding a second or third match. It doesn't need to process the 
entire table. It just needs to find one.

---

##### 2. How the Search Actually Works: The Correlated Subquery

The power and efficiency of `EXISTS` come from its typical use as a **correlated 
subquery**. Let's break down the process using our classic example:

**Goal:** Find all employees who work in a valid department.

```sql
SELECT *
FROM employees e
WHERE EXISTS (
    SELECT 1 -- It could be SELECT 1, SELECT NULL, SELECT 'X'. The value is ignored.
    FROM departments d
    WHERE d.department_id = e.department_id -- This is the correlation!
);
```

Here is the step-by-step search process for this query:

1.  **Outer Query Starts:** The database reads a row from the outer `employees` table.
    Let's say it gets an employee with `department_id = 80`.

2.  **Inner Query Executes (Once per Outer Row):** For this specific employee, the 
    database now executes the inner query, **plugging in the value** from the current 
    outer row. The query effectively becomes:
    `SELECT 1 FROM departments d WHERE d.department_id = 80;`

3.  **The Search (Short-Circuit):** The database starts searching the `departments` 
    table for a row where `department_id = 80`.
   *   **If it finds ONE row:** It instantly stops searching the `departments` table. 
       The `EXISTS` clause is satisfied, and the current employee row is added to the 
       result set.
   *   **If it finds NO rows:** After a full scan (or index lookup) finds no matches,
       it concludes `FALSE`, and the employee row is discarded.

4.  **Repeat:** This process repeats for every single row in the `employees` table. 
    Each employee triggers a new, independent search in the `departments` table.

##### 3. The Critical Role of Indexes

The performance of an `EXISTS` clause is almost entirely dependent on the **indexes** 
on the table in the subquery (the `departments` table in our example).

*   **With an Index (Highly Efficient):** If there is an index on `departments.department_id`, 
    the search in step 3 is not a full table scan. It's an extremely fast **index 
    seek**. The database can instantly find whether the value `80` exists in the index. 
    This makes the per-row cost of the `EXISTS` clause very low.
   *   **Performance:** O(n log m) or better, where `n` is the size of the outer 
       table and `m` is the size of the subquery table.

*   **Without an Index (Very Slow):** If there is no index on `departments.department_id`, 
    the database must perform a **full table scan** of the `departments` table for 
    *every single employee* to see if the ID exists. This is disastrous for performance.
   *   **Performance:** O(n * m) (a "nested loop" full scan), which is the worst-case scenario.

This makes `EXISTS` the superior choice when you can use an indexed column to 
correlate a large subquery table with a smaller outer result set.

----------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------


IN clause: The inner subquery runs once at the beginning because it is independent of 
the outer query. It collects all possible outcomes into a set.
EXISTS clause: The inner subquery runs (conceptually) for each row of the outer query,
checking for the existence of a match in the inner table, and stops as soon as it 
finds one.
This theoretical distinction is crucial for understanding how the two clauses work.
However, the key practical nuance, as mentioned earlier, is that modern database 
query optimizers are incredibly sophisticated. They often translate both statements 
internally into the most efficient execution plan (usually a form of semi-join), 
often blurring the performance difference between them in real-world scenarios.
So, while your description of the logical operation is correct, the physical 
execution might look very similar for both in a high-performance database system.