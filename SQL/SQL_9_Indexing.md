# Lecture | SQL 9: Indexing

## I] `explain` keyword:

```sql
EXPLAIN SELECT * FROM actor WHERE first_name = 'JOHN';
```

### 1.1) What `EXPLAIN` Does

The `EXPLAIN` keyword, when prepended to a `SELECT` (or any other) statement, **does 
not execute the query itself**. Instead, it asks the database's query planner to **show
the execution plan** it would use to perform that query.

In simple terms, `EXPLAIN` answers the question: **"How would you find this data?"** 
instead of **"Go find this data."**

### 1.2) Why You Use `EXPLAIN`

The primary purpose is **performance optimization**. By seeing the execution plan, 
you can understand:

1.  **Is the query using an index?** (This is the most common reason to use `EXPLAIN`).
2.  How is it joining tables (if multiple tables are involved)?
3.  How many rows is it expecting to examine? (`rows`).
4.  What is the estimated cost of the operation? (`cost`).
5.  Is it doing a full table scan, which is often inefficient on large tables?

---

## II] B+ Trees:
**B+ trees** are a crucial data structure, especially in the world of databases and 
file systems. They are an advanced evolution of the simpler BSTs we just discussed, 
designed specifically for systems that read and write large blocks of data from disk.

### 2.1) The Core Problem: Why Not Use Normal BSTs?

Databases store enormous amounts of data that can't fit in RAM. They must read from 
and write to the hard disk (or SSD). Disk access is **incredibly slow** compared to 
RAM access (thousands of times slower).

A balanced binary search tree (like a Red-Black tree) has an excellent **O(log n)** 
search time. However, this "log n" is the number of *comparisons*. The problem is that
each node in a BST might be stored in a different, random location on the disk. Every 
time you follow a pointer to a new node, it could require a new, slow disk read. For 
a large database, the tree would be very "tall," leading to many disk reads and 
terrible performance.

**B+ trees solve this by being "short and fat".**

---

### 2.2) What is a B+ Tree?

A B+ tree is a self-balancing tree data structure that maintains sorted data and 
allows for efficient insertion, deletion, and search operations. It is optimized for 
systems that read and write large blocks of data.

Here are its defining characteristics, which are best understood by looking at a 
diagram:


#### 1. Nodes Have High Fan-out (They are "Fat")
Each node can have **many keys** and **many children** (not just two). The maximum 
number of keys is called the *order* of the B+ tree. Because a single node can hold 
many keys, the tree becomes very "short" (has very few levels), even for massive 
datasets.

*   A short tree means **fewer disk reads** are needed to find any specific piece 
of data.

#### 2. Separation of Internal and Leaf Nodes
*   **Internal Nodes (Non-Leaf Nodes):** These nodes **only store keys** and pointers
    to child nodes. They act as a sophisticated routing map to guide the search to 
    the correct leaf node. **They do not store the actual data values.**
*   **Leaf Nodes:** These nodes store **all the keys and their associated data values** 
    (or pointers to the actual data on disk). This is a key difference from B-trees, 
    where internal nodes also store data.

#### 3. Linked Leaf Nodes
All leaf nodes are linked together in a **doubly-linked list**. This is a killer 
feature for databases.

### Why are These Features So Powerful?

1.  **Efficient Searches:**
    *   The tree is short, so finding a record requires very few disk reads.
    *   **Range Queries are extremely fast:** A query like "find all employees with 
        IDs between 100 and 500" is inefficient in a normal BST. In a B+ tree, you 
        find the first ID (100), and then simply **walk along the linked list of leaf
        nodes** until you exceed 500. This avoids having to traverse back up the tree.

2.  **Efficient Disk Access:**
    *   The "fat" nodes are designed to fit into a **single block** of data read 
        from the disk (e.g., a 4KB block). Reading one block from a disk that contains 
        100 keys is far faster than reading 100 blocks that each contains one key.

3.  **Predictable Performance:**
    *   Because the tree is always balanced and all leaves are at the same depth, the
        time to find any record is consistently fast (**O(log n)**), which is critical
        for database performance.

In essence, the B+ tree is the workhorse of modern databases. Whenever you run an SQL
query, a B+ tree (or its cousin, the B-tree) is almost certainly working behind the 
scenes to find your data quickly and efficiently.

---

## III] Why HashMaps are not good for Indexing?

Here’s a breakdown of why databases and other storage systems typically prefer 
structures like **B+Trees** over HashMaps for indexing:

### 3.1) Inability to Support Range Queries and Sorting

This is the single biggest reason.

*   **How HashMaps Work:** A hash function takes a key and maps it to a seemingly
    random bucket or memory address. The keys are **not stored in any sorted order**.
*   **The Problem:** A very common database operation is a range query: 
    `SELECT * FROM employees WHERE salary BETWEEN 50000 AND 80000;` or 
    `SELECT * FROM customers ORDER BY last_name;`
*   **Why HashMaps Fail:** To perform these queries with a HashMap, you would have to 
    check *every single bucket* for keys that fall within that range. This results 
    in a full table scan (**O(n)** time), completely negating any performance benefit
    the index was supposed to provide.

**B+Trees**, in contrast, store keys **in sorted order**. Finding the starting point
  of a range is very fast (**O(log n)**), and then you can simply traverse the leaf
  nodes to get all subsequent, sorted values efficiently.

### 3.2) Lack of Ordering

Related to the point above, the inherent nature of hashing destroys any natural order
of the keys. For database indexing, having data sorted is often just as important as 
finding a single record quickly. Operations like `ORDER BY`, `GROUP BY`, finding the
`MIN()` or `MAX()`, or getting the "first 10" records are extremely inefficient with 
a HashMap.

### 3.3) Performance Degradation and Costly Resizing

*   **Load Factor and Resizing:** HashMaps maintain a "load factor" (e.g., 0.75). When
    the number of elements exceeds this factor multiplied by the capacity, the 
    underlying array must be **resized** (typically doubled). This operation is very 
    expensive (**O(n)**) as it requires rehashing every single existing key to a new 
    bucket in the larger array.

**B+Trees** grow and shrink gracefully by splitting and merging nodes. Their 
performance remains a predictable **O(log n)** regardless of size, and the log base
is very large (hundreds), making the tree very shallow.

### 3.4) Inefficient with Partial Key Lookups

*   **The Scenario:** Imagine an index on `(last_name, first_name)`.
*   **A HashMap's Problem:** A hash function would need the *entire* key
    `(last_name, first_name)` to compute a bucket. A query for 
    `WHERE last_name = 'Smith'` cannot use the index effectively because it doesn't 
    have the full key.
*   **Why B+Trees Win:** B+Trees are excellent at partial key lookups. The keys are 
    sorted first by `last_name`, then by `first_name`. The tree can quickly find the 
    first `'Smith'` entry and then traverse the leaf nodes to get all Smiths.

---
