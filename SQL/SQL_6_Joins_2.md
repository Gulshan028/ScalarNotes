# Lecture | SQL 6: Joins - 2

## CONTENTS:
1. Points to Remember
2. SPECIAL CASE

## I] Points to Remember:
1. MySQL doesn't support `FULL OUTER JOIN`, however other DBMS like PostreSQL, etc might
   support it. Although, `FULL OUTER JOIN` is the correct query as per SQL language standard.

---

## II] SPECIAL CASE:
### 2.1) My Question:
My simple question is:
if I have `NULL` `customer_id` on both the tables, and I am joining the tables based 
on this customer_id as:
```sql
select
from table1 t1
join table2 t2
on t1.customer_id IS NULL and t2.customer_id IS NULL;
```
can this be a valid query, tell me with the help of one example?

### 2.2) **ANSWER:**

Yes, this is absolutely a **valid query**!

When you join on `t1.customer_id IS NULL AND t2.customer_id IS NULL`, you're matching
rows where both tables have NULL values in the customer_id column. This creates a 
relationship based on the **absence of data** rather than matching values.

#### (1) Example with Sample Data

Let's create two tables with NULL customer_id values:

```sql
-- Create tables
CREATE TABLE customer_contacts (
    contact_id INT PRIMARY KEY,
    customer_id INT,
    contact_name VARCHAR(50),
    phone VARCHAR(15)
);

CREATE TABLE customer_notes (
    note_id INT PRIMARY KEY,
    customer_id INT,
    note_text VARCHAR(100)
);

-- Insert sample data
INSERT INTO customer_contacts VALUES 
(1, 101, 'John Doe', '555-0101'),
(2, NULL, 'Unknown Caller', '555-9999'),  -- NULL customer_id
(3, 102, 'Jane Smith', '555-0102'),
(4, NULL, 'Mystery Contact', '555-8888'); -- NULL customer_id

INSERT INTO customer_notes VALUES 
(1, 101, 'Regular customer - prefers email'),
(2, NULL, 'Need to identify this customer'),  -- NULL customer_id
(3, NULL, 'Follow up on anonymous inquiry'),   -- NULL customer_id
(4, 103, 'New customer - welcome call needed');
```

#### (2) Your Query in Action:

```sql
SELECT 
    c.contact_name, 
    c.phone,
    n.note_text
FROM customer_contacts c
JOIN customer_notes n 
    ON c.customer_id IS NULL 
    AND n.customer_id IS NULL;
```

#### (3) Result:
```
contact_name    | phone      | note_text
----------------|------------|---------------------------
Unknown Caller  | 555-9999   | Need to identify this customer
Unknown Caller  | 555-9999   | Follow up on anonymous inquiry
Mystery Contact | 555-8888   | Need to identify this customer
Mystery Contact | 555-8888   | Follow up on anonymous inquiry
```

#### (4) How This Works:

1. **customer_contacts** has 2 records with NULL customer_id (rows 2 and 4)
2. **customer_notes** has 2 records with NULL customer_id (rows 2 and 3)
3. The join creates a **Cartesian product** within the NULL groups:
    - Each NULL contact matches with each NULL note
    - 2 contacts × 2 notes = 4 result rows

#### (5) When This is Useful:

This type of join is particularly useful for:
- **Data quality issues**: Finding unassigned/unidentified records
- **Orphan records**: Records that haven't been properly linked
- **Special categories**: Handling "unknown" or "unassigned" categories systematically

So yes, your query is perfectly valid and serves a specific purpose in data analysis 
and quality checking!

---

## III] Links:
1. https://stackoverflow.com/questions/4796872/how-can-i-do-a-full-outer-join-in-mysql

---