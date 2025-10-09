# Lecture | SQL 12: Schema Design 1

## I] Naming Convention in SQL
### 1. The Overarching Principle: Consistency

The most important rule is to **choose a style and stick to it across your entire 
database**. Mixing styles (e.g., `snake_case` for tables but `PascalCase` for columns)
is a common source of confusion.

---

### 2. Case Styles: snake_case vs. camelCase vs. PascalCase

This is the most fundamental choice.

| Style | Example | Best For | Notes |
| :--- | :--- | :--- | :--- |
| **snake_case** | `product_category`, `user_id` | **Tables, Columns, Constraints** | **The most popular and highly recommended style for SQL.** It's readable and avoids case-sensitivity issues. |
| **PascalCase** | `ProductCategory`, `UserId` | Tables (sometimes) | Common in Microsoft SQL Server ecosystems. Can be confused with class names in OOP. |
| **camelCase** | `productCategory`, `userId` | **Avoid in SQL** | Rarely used in SQL because it doesn't align well with SQL's case-insensitive tradition for unquoted identifiers. |

**Recommendation: Use `snake_case` for all identifiers.** It works seamlessly across 
all database systems (PostgreSQL, MySQL, SQL Server, SQLite) without needing to quote 
identifiers.

---

### 3. Naming Specific Database Objects

#### **Tables**
*   **Use nouns in the plural form.** A table is a set of entities.
    *   **Good:** `users`, `products`, `orders`, `order_items`
    *   **Avoid:** `user`, `product`, `User`, `Product`
*   **Be clear and descriptive.** Avoid abbreviations unless they are universally understood.
    *   **Good:** `departments`
    *   **Avoid:** `dpt`, `dept_list`

#### **Columns**
*   **Use singular nouns.**
    *   `id`, `name`, `price`, `created_at`
*   **For foreign keys, prefer the singular name of the referenced table + `_id`.**
    *   In the `orders` table, the column pointing to `users.id` should be `user_id`.
    *   This makes JOIN conditions intuitive: `... ON orders.user_id = users.id`
*   **Avoid using just the table name as a column name.**
    *   **Bad:** A table `users` with a column called `user`. Be more specific 
        (`username`, `full_name`).

#### **Primary Keys**
*   **Simply name the column `id`.** It's clean, consistent, and every ORM expects it.
    *   `CREATE TABLE users (id SERIAL PRIMARY KEY, ...);`
*   **Alternative (legacy):** `user_id` or `users_id`. This is more explicit but 
      redundant. `id` is almost always understood in context.

#### **Foreign Keys**
*   As mentioned above, use the referenced table's singular name + `_id` (e.g., `user_id`, `product_id`).
*   **For composite or specific relationships,** be more descriptive:
    *   If a `bug` can be assigned to a `user` and also reported by a `user`, 
        use `assigned_user_id` and `reported_user_id`.

#### **Junction Tables (for Many-to-Many Relationships)**
*   Name the table after the two tables it joins, in alphabetical order.
    *   Table linking `users` and `roles`: `users_roles`.
*   The composite primary key is often the two foreign keys: `(user_id, role_id)`.

#### **Indexes**
*   **Be descriptive and include the table and column names.**
    *   **Format:** `idx_<table_name>_<column_name>`
    *   **Example:** `idx_users_email` (an index on the `email` column of the `users` 
          table).
    *   **For multi-column indexes:** `idx_users_name_country` (index on `name` and 
          `country`).

---

### 4. Syntax and Formatting Rules

*   **Use underscores (`_`):** They are the standard separator in `snake_case`.
*   **Avoid quotes:** Write identifiers in lower case `snake_case` so you never have 
      to use double quotes (`"MixedCase"`). Quoted identifiers become case-sensitive 
      and are a major hassle.
*   **Don't use SQL keywords:** Avoid names like `user`, `order`, `timestamp`, `group`. 
      If you absolutely must, you'll have to quote them forever. Use synonyms 
      instead (`user_account`, `purchase_order`, `created_at`, `grouping`).

---

### Summary of Key Recommendations

1.  **Use `snake_case` for everything** (tables, columns, constraints).
2.  **Name tables as plural nouns** (`users`).
3.  **Name columns as singular nouns** (`name`, `created_at`).
4.  **Name primary keys simply `id`**.
5.  **Name foreign keys as `<singular_referenced_table>_id`** (`user_id`).
6.  **Be explicit and descriptive** with constraint and index names using prefixes 
      (`uq_`, `fk_`, `idx_`).
7.  **Avoid SQL keywords and quoted identifiers** at all costs.

By following these conventions, your SQL schemas will be self-documenting, easier to
query, and a joy for other developers to work with.

----------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------