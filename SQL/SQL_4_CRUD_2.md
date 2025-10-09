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
