# Lecture | SQL 5: Joins - 1

## CONTENTS:

## I] JOINS:
```sql
select s.name, b.name
from students s 
join referrals r
on s.no_of_certifications > r.certificates_required;
```
**Question:** Is this a valid sql query?

**Answer:**
Yes, this is a **valid SQL query** from a syntax perspective. The query will execute 
without errors, but it may produce unexpected results depending on your intent.

### ✅ **Syntactically Valid**
- Proper `SELECT` clause
- Correct `JOIN` syntax with table aliases
- Valid `ON` condition using comparison operator `>`
- All SQL syntax rules are followed

### ⚠️ **Potential Issues & Considerations**

**1. Many-to-many relationship**: This will join each student with every referral where:
- The student's certification count exceeds the referral's requirement
- This could create duplicate student records in the results

**2. Business logic question**: Are you trying to find:
- Students who qualify for specific referrals? ✓ (this query does that)
- Or something else entirely?

**3. Example results**: If you have:
- Students: Alice (5 certs), Bob (3 certs)
- Referrals: JobA (requires 4), JobB (requires 2)

The query would return:
- Alice with JobA (5 > 4) and JobB (5 > 2)
- Bob with JobB only (3 > 2)

### **Alternative interpretations**:
If you meant something different, you might want:

**To find students who don't qualify:**
```sql
SELECT s.name, r.name
FROM students s 
CROSS JOIN referrals r
WHERE s.no_of_certifications <= r.certificates_required;
```

**To find the best matching referral for each student:**
```sql
SELECT s.name, r.name
FROM students s 
JOIN referrals r ON s.no_of_certifications >= r.certificates_required
WHERE r.certificates_required = (
    SELECT MAX(certificates_required) 
    FROM referrals r2 
    WHERE s.no_of_certifications >= r2.certificates_required
);
```

The query is **valid SQL** but make sure the business logic (students with more 
certs than required) matches your actual requirements.

---

## II] Aliases:
### 2.1) **Table Aliases**
- **`AS` is optional** for table aliases
- Both forms are valid and equivalent:

```sql
-- Without AS (most common)
SELECT s.name, b.batch_name
FROM students s
JOIN batches b ON s.batch_id = b.id;

-- With AS (less common for tables)
SELECT s.name, b.batch_name
FROM students AS s
JOIN batches AS b ON s.batch_id = b.id;
```

### 2.2) **Column Aliases**
- **`AS` is optional but recommended** for column aliases for clarity
- Both forms work, but using `AS` makes it more readable:

```sql
-- With AS (recommended for clarity)
SELECT 
    s.name AS student_name,
    b.batch_name AS batch_title,
    COUNT(*) AS total_students
FROM students s
JOIN batches b ON s.batch_id = b.id;

-- Without AS (works but less clear)
SELECT 
    s.name student_name,
    b.batch_name batch_title,
    COUNT(*) total_students
FROM students s
JOIN batches b ON s.batch_id = b.id;
```

### 2.3) **Key Points:**
1. **Table aliases**: `AS` is optional and often omitted for brevity
2. **Column aliases**: `AS` is optional but highly recommended for readability
3. **Best practice**: Use `AS` with column aliases, omit it for table aliases

So your understanding is perfectly correct! 👍

---

## III] LINKS:
1. Anshuman singh notes: https://github.com/anshumansingh/SQLNotes/