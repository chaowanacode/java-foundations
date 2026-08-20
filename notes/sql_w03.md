# W3 — SQL (JOIN, Index)

## Table of Contents

- [🔧 JOIN](#-join)
- [🔧 Index](#-index)

## JOIN

A JOIN in MySQL is a clause used to combine rows from two or more tables based on a related column between them, such as a foreign key.

- INNER JOIN: Returns only the rows that have matching values in both tables. Excludes any records that do not have a corresponding match.
- LEFT JOIN: Returns all rows from the left table, and the matched rows from the right table. If there is no match, the result will contain NULL values for the right table's columns.
- RIGHT JOIN: Opposite of a left join; returns all rows from the right table, and the matched rows from the left table. If no match exists, the result contains NULL values for the left table's columns.
- SELF JOIN: A special type of join where you join a table to itself by creating an alias (a nickname) for the table copy. Particularly useful for displaying hierarchical data, such as showing which employee reports to which supervisor.

### Example queries (see `sql/w03/joins.sql`)

```sql
-- INNER JOIN
SELECT transaction_id, amount, first_name, last_name
FROM transactions
INNER JOIN customers
ON transactions.customer_id = customers.customer_id;
```

```sql
-- LEFT JOIN
SELECT transaction_id, amount, first_name, last_name
FROM transactions
LEFT JOIN customers
ON transactions.customer_id = customers.customer_id;
```

```sql
-- RIGHT JOIN
SELECT transaction_id, amount, first_name, last_name
FROM transactions
RIGHT JOIN customers
ON transactions.customer_id = customers.customer_id;
```

### Self join example (see `sql/w03/joins.sql`)

```sql
-- SELF JOIN (INNER)
SELECT
    a.customer_id,
    a.first_name,
    a.last_name,
    CONCAT(b.first_name, ' ', b.last_name) AS referred_by
FROM customers AS a
INNER JOIN customers AS b
ON a.referral_id = b.customer_id;
```

```sql
-- SELF JOIN (LEFT JOIN variant, includes customers with no referral)
SELECT
    a.customer_id,
    a.first_name,
    a.last_name,
    CONCAT(b.first_name, ' ', b.last_name) AS referred_by
FROM customers AS a
LEFT JOIN customers AS b
ON a.referral_id = b.customer_id;
```

## Index
