# Lecture | SQL 10: Transactions

## I] Points to remember:
1. Another session means: In the MySQL Workbench, while starting it, we select our username
   and insert our password, also this is for a particular host. 
   - Now, if we have another username and password then it is called as another session.
2. In MySQL, `autocommit` is set to `true`, by default. The purpose of the `commit` keyword
   is to write the changes back to Database. 
    - now, if `autocommit` is off then the changes made in one session are not visible 
      to other session or other user. Since, the data has not been persisted i.e. we 
      haven't written the data back to the database.
    - For this purpose, we need to use keyword `commit;` to save the changes to the database.
3. We can use the keyword `rollback` to revert the changes made in the current session 
   back to the previous commit level. But, for this `autocommit` must be off, otherwise
   the data will be committed automatically once the query is run successfully.
4. Yes, write operations (UPDATE, DELETE, INSERT) always acquire locks, regardless of
   the isolation level. Writes always need locks to maintain atomicity and consistency.
   The isolation level is a mechanism that controls the locking strategy for read 
   operations to create a trade-off between performance (concurrency) and data 
   consistency.

---

## II] **Phantom Read**

### 2.1) The Core Idea: What is a Phantom Read?

A **phantom read** is a specific type of concurrency anomaly that can occur in a 
database system when a transaction retrieves a set of rows multiple times but gets 
different results each time, even though it hasn't modified the data itself.

In simpler terms, it's when a transaction sees **new, "phantom" rows** that magically
appear between its reads because another concurrent transaction committed new data.

---

### 2.2) The Official SQL Standard Definition

> **P3 (Phantom):** Transaction T1 reads a set of rows N that satisfy some search 
condition. Transaction T2 then creates (i.e., INSERTs) one or more rows, and those 
newly inserted rows satisfy the search condition T1 used. If T1 repeats the initial 
read with the same condition, it gets a different set of rows.

### 2.3) A Simple Analogy

Imagine you're counting the number of people in a room (Transaction T1).
1.  You count: 10 people.
2.  While you're counting, someone opens a secret door and lets 2 more people in (Transaction T2 commits an `INSERT`).
3.  You count again to verify your number. Now there are 12 people!
4.  You are confused because you saw a "phantom" person (or two) appear out of nowhere.

This is a phantom read.

---

### 2.4) A Concrete Database Example

Let's see how this plays out with two concurrent database transactions:

| Time | Transaction 1 (T1)                                                                 | Transaction 2 (T2)                                                                 | Result / Explanation                                                                                                                              |
| :--- | :--------------------------------------------------------------------------------- | :--------------------------------------------------------------------------------- | :------------------------------------------------------------------------------------------------------------------------------------------------ |
| 1    | `START TRANSACTION;`                                                               |                                                                                    | T1 begins.                                                                                                                                        |
| 2    | `SELECT * FROM employees WHERE department = 'Sales';` <br> **Returns:** 5 rows     |                                                                                    | Initial read.                                                                                                                                     |
| 3    |                                                                                    | `START TRANSACTION;`                                                               | T2 begins.                                                                                                                                        |
| 4    |                                                                                    | `INSERT INTO employees (name, department) VALUES ('Jane Doe', 'Sales');`           | T2 adds a new Sales employee.                                                                                                                     |
| 5    |                                                                                    | `COMMIT;`                                                                          | **This is the crucial moment.** T2's new row is now permanently saved and visible to new transactions.                                            |
| 6    | `SELECT * FROM employees WHERE department = 'Sales';` <br> **Returns:** **6 rows** |                                                                                    | T1 runs the same query again. It now sees the new row committed by T2. This is the **Phantom Read**.                                              |
| 7    | `COMMIT;`                                                                          |                                                                                    | T1 ends. The phantom read has occurred within its transaction.                                                                                    |

**The problem for T1:** The result of its operation is not consistent. It performed
two identical reads within a single transaction and got two different results. This 
can lead to logical errors in application code (e.g., incorrect calculations, reports,
or decisions based on the data).

---

### 2.5) How to Prevent Phantom Reads: Isolation Levels

The solution to phantom reads is to use a stricter **transaction isolation level**. 
The SQL standard defines four levels:

1.  **READ UNCOMMITTED:** Allows all concurrency issues (dirty reads, non-repeatable 
      reads, phantoms).
2.  **READ COMMITTED:** Prevents dirty reads, but allows non-repeatable reads and 
      phantoms.
3.  **REPEATABLE READ:** Prevents dirty reads and non-repeatable reads. **In some 
      databases (like MySQL's InnoDB), it also prevents phantom reads** using next-key locks.
4.  **SERIALIZABLE:** The strictest level. Prevents all concurrency issues by executing
      transactions in a serial order. This is the only level guaranteed to prevent 
      phantom reads in all databases.

**How to set it:**
```sql
SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
START TRANSACTION;
-- Your SELECT statements here
COMMIT;
```
---

### 2.6) Isolation Level Cheat Sheet

| Isolation Level      | Dirty Read | Non-Repeatable Read | **Phantom Read** |
| :------------------- | :--------- | :------------------ | :--------------- |
| **READ UNCOMMITTED** | ✅ Possible  | ✅ Possible           | ✅ Possible        |
| **READ COMMITTED**   | ❌ Prevented | ✅ Possible           | ✅ Possible        |
| **REPEATABLE READ**  | ❌ Prevented | ❌ Prevented          | ✅ Possible*       |
| **SERIALIZABLE**     | ❌ Prevented | ❌ Prevented          | ❌ Prevented       |

### 2.7) Breakdown by Level:

1.  **READ UNCOMMITTED:** This is the wild west. A transaction can see uncommitted 
      changes from others (dirty reads). It has no protection against anything, so 
      phantom reads are definitely possible and expected.

2.  **READ COMMITTED:** This level steps things up by ensuring a transaction only sees
    data that has been **committed** by other transactions. It prevents dirty reads.
   *   However, if another transaction **commits** a new row (`INSERT`) or deletes a 
       row (`DELETE`) that matches your query's search condition, your transaction 
       *will* see that change on subsequent reads. This is the textbook definition 
       of a phantom read, and it **is allowed** in READ COMMITTED.

3.  **REPEATABLE READ:** This is where your point is most insightful. The SQL standard
    defines REPEATABLE READ as only being required to prevent **Non-Repeatable Reads**
    (changes to existing rows). The standard allows phantom reads to occur at this level.
   
4.  **SERIALIZABLE:** This is the only isolation level the SQL standard **guarantees**
    will prevent phantom reads across all databases. It typically uses the most
    restrictive locking or advanced concurrency control methods like MVCC with 
    predicate locking to achieve this.

### 2.8) Conclusion:

**Phantom reads are a problem that is *prevented* by the SERIALIZABLE isolation 
level and is *allowed* in the READ UNCOMMITTED, READ COMMITTED, and (by the SQL 
standard definition) REPEATABLE READ levels.**

---

## III] Serializable 

### 3.1) The Core Principle: Illusion of Serial Execution

The fundamental guarantee of SERIALIZABLE isolation is that the outcome of running 
multiple concurrent transactions is guaranteed to be **the same as if they had been 
executed one after the other, in some serial order**.

This doesn't mean transactions *actually* run one at a time (that would kill 
performance). Instead, the database's concurrency control mechanisms (like 
sophisticated locking or MVCC) work behind the scenes to create this illusion, 
ensuring that even though operations are interleaved, the final result is serializable.

---

### 3.2) What Problems Does It Solve?

A SERIALIZABLE isolation level prevents all three of the classic concurrency anomalies:

1.  **Dirty Reads:** Impossible. A transaction cannot see uncommitted changes from 
      another transaction.
2.  **Non-Repeatable Reads:** Impossible. Once a transaction reads a value, that value
      cannot be changed by another transaction until it completes.
3.  **Phantom Reads:** Impossible. This is the key differentiator. If a transaction 
      re-runs a query with a `WHERE` clause (e.g., `SELECT * FROM users WHERE age > 30`), 
      it is guaranteed to get the exact same set of rows. No new rows ("phantoms") 
      that match the condition can be inserted, updated, or deleted by other 
      transactions until the first transaction finishes.

---

### 3.3) How Is This Achieved? (The Implementation)

Databases use one of two primary methods to enforce serializability:

#### 1. Locking-Based (Pessimistic Concurrency)

This is the classic method.
*   **Range Locks (Next-Key Locking):** Instead of just locking existing rows that 
    are read or modified, the database locks **ranges of index keys**. For example, 
    a `SELECT ... WHERE age > 30` would place a lock on the index range for `age > 30`. 
    This lock prevents any other transaction from inserting, updating, or deleting 
    any row that would fall into that range (e.g., inserting a user with `age=35`).
*   **Strict Two-Phase Locking (2PL):** Transactions acquire all necessary locks 
    (shared locks for reads, exclusive locks for writes) during their execution and 
    release them *only* at the very end (commit or rollback). This ensures no 
    conflicts can arise during the entire transaction lifespan.

#### 2. Multi-Version Concurrency Control (MVCC) with Serializability Detection (Optimistic Concurrency)

This is a more modern approach used by databases like PostgreSQL and Oracle.
*   **How it works:** Each transaction sees a snapshot of the database as it existed
    at the start of the transaction. Transactions are allowed to proceed without 
    blocking each other.
*   **The Catch:** Before a transaction can commit, the system performs a 
    **serializability check**. It analyzes the read/write sets of all concurrent 
    transactions. If it detects that committing this transaction would break the 
    serializable illusion (e.g., it wrote a value that another transaction depended 
    on in its read), one of the transactions is forced to roll back with a 
    **serialization failure** error.
*   **This feels like:** "Okay, everyone do your work freely. But at the end, we'll
    check if your work steps could have logically happened one after the other. If 
    not, someone has to start over."

---

## IV] LINKS:
1. amu university database course:https://15445.courses.cs.cmu.edu/fall2022/schedule.html
    It is a very detailed course. This is one of the best courses on the databases in the world.
2. Different Isolation Levels: https://www.cockroachlabs.com/blog/sql-isolation-levels-explained/
3. Phantom read: https://medium.com/@nikhillad01/understanding-phantom-reads-in-databases-ddc8ffa36e4c