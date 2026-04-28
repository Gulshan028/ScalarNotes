# Indexing 

## 1) Introduction to Indexing:
- A database stores its data in disk. Reading data from disk is `80x` slower than reading 
  from RAM! We all know that on disk DB stores data of each row one after the other. When 
  data is fetched from disk, OS fetches data in forms of blocks. That means it reads not 
  just the location that you want to read, but also locations nearby. 
- Inside the database, data is organized in memory blocks known as shards.
- First OS fetches data from disk to memory, then CPU reads from memory. 
- The index of a database helps go to the correct block of the disk fast.

### 1.1) How Searching Happens in Indexed Database?:
The database does a search in the B-tree for a given key and returns the index in 
`O(log(n))` time. The record is then obtained by running a second B+tree search in 
`O(log(n))` time using the discovered index. So overall approx time taken for 
searching a record in a B-tree in DBMS Indexed databases is O(log(n)).

### 1.2) Cons of Indexes:
There are two big problems that can arise with the use of index:
1. Writes will be slower
2. Extra storage

Thus, it is recommended to use index if and only if you see the need for it. 
Don't create indexes prematurely.

## 2) Indexes on Multiple Columns
- If I create an index on the `id` column, the tree map used for storing data will allow
  for faster retrieval based on that column. However, a query like:
```sql
select * from students where psp = 90.1;
```
will not be faster with this index. The index on `id` has no relevance to the `psp` 
column, and the query will perform just as slowly as before. Therefore, we need to 
create an index on the column that we are querying.
- Let's say we create an index on (id, name). When we create index on these 2 columns,
  it is indexed according to `id` first and then if there is a tie it, will be indexed
  on `name`.
- Thus, if we create an index on (id, name), it will actually not help us on the 
  filter of name column.

## 3) Indexing on Strings:
Consider a query:
```sql
SELECT * FROM user WHERE email = 'abc@scaler.com';
```
- This query is very slow, so we will definitely create an index on the email column. 
  So, the map that is created behind the scenes using indexing will have email mapped to
  the corresponding block in the memory.
- Now, instead of creating an index on whole email, we can create an index for the 
  first part of the email (text before @) and have a list of blocks (for more than 
  one email having same first part) mapped to it. Hence, the space is saved.
- Typically, with string columns, index is created on prefix of the column instead of
  the whole column. It gives enough increase in performance
- Consider the query:
```sql
SELECT * FROM user
WHERE address LIKE '%ambala%';
```
- We can see that indexing will not help in such queries for pattern matching. In such
  cases, we use Full-Text Index.

## 4) How to create index
