# W3 — SQL (JOIN, Index, Transactions, EXPLAIN)

## Table of Contents

- [🔧 JOIN](#-join)
- [🔧 Index](#-index)
- [🔒 Transaction Isolation Levels](#-transaction-isolation-levels)
- [🔐 Table Locks vs Row Locks](#-table-locks-vs-row-locks)
- [💀 Deadlocks](#-deadlocks)
- [🔍 SQL EXPLAIN](#-sql-explain)

## 🔧 JOIN

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

## 🔧 Index

An index is a type of data structure (B-tree) used to find values within a specific column more quickly. MySQL normally searches sequentially through a column; the longer the column, the more expensive that search becomes.

- Applying an index speeds up searching/selecting, but slows down updating — a table that's frequently updated (e.g. transactions) is a poor index candidate, while a rarely-updated table (e.g. customers) benefits more.
- Multi-column indexes follow a leftmost prefix rule: columns must be queried in the order they were indexed. A `(last_name, first_name)` index speeds up searches by `last_name` alone, or by `last_name` + `first_name` together, but not by `first_name` alone.
- `SHOW INDEXES FROM <table>` lists current indexes. `DROP INDEX <name> ON <table>` (or `ALTER TABLE ... DROP INDEX`) removes one.

### Example queries (see `sql/w03/indexes.sql`)

```sql
-- Single-column index
CREATE INDEX last_name_idx
ON customers (last_name);
```

```sql
-- Multi-column index (leftmost prefix: last_name must come first)
CREATE INDEX last_name_first_name_idx
ON customers (last_name, first_name);
```

```sql
-- Drop the now-redundant single-column index
ALTER TABLE customers DROP INDEX last_name_idx;
```

## 🔒 Transaction Isolation Levels

Controls what one session sees while another is modifying the same data. Check with `SHOW VARIABLES LIKE '%isolation%'`, change with `SET SESSION TRANSACTION ISOLATION LEVEL ...`

- **READ UNCOMMITTED** — dirty reads; you see another session's changes before it commits.
- **READ COMMITTED** — you only see committed data. Recommended for OLTP/e-commerce.
- **REPEATABLE READ** — MySQL's actual default. Inside a transaction, repeated reads return the same value even if another session commits in between.
- **SERIALIZABLE** — strictest. Even a plain `SELECT` locks the row, so updates from other sessions wait (and can time out).

Demos use `autocommit=0` + `START TRANSACTION` so commits are explicit — see `sql/w03/isolation_levels.sql` (run it with two sessions side by side).

## 🔐 Table Locks vs Row Locks

See `sql/w03/locks.sql` (three sessions: seller, buyer 1, buyer 2).

Why locks exist: the **lost-update problem**. Seller reads qty 40 and writes 100 (+60); buyer also read 40 and writes 38 (−2). The seller's update is lost, data is corrupted.

- **Table lock** — seller locks the whole `products` table for write. Buyer 1 (same book) hangs, Buyer 2 (different book) hangs, and even a browse/`SELECT` hangs. Concurrency collapses.
- **Row lock** (InnoDB default) — only the affected row is locked. Other rows update fine, and `SELECT` on any row still works. Blocked sessions eventually time out.
- Inspect with a query on `performance_schema.data_locks`: you'll see an IX (intention exclusive) entry at table level plus a record lock on the specific key value (e.g. `product_id = 1`).

## 💀 Deadlocks

See `sql/w03/deadlocks.sql` (two sessions, run in the numbered order).

Two transactions each hold a row lock the other needs → circular wait.

- Session A updates row 1, Session B updates row 2 (fine, independent locks). Then B tries row 1 and A tries row 2 → deadlock.
- InnoDB detects it and kills one session: error 1213, "Deadlock found when trying to get lock; try restarting transaction." The victim's transaction is rolled back; the other one proceeds.
- Prevention: keep transactions short and always touch rows in a consistent order.

## 🔍 SQL EXPLAIN

See `sql/w03/explain.sql`.

`EXPLAIN <query>\G` shows the execution plan; `EXPLAIN FORMAT=JSON` adds query cost.

Columns that matter: `type` (ALL = full table scan, bad), `possible_keys`, `key` (the one actually chosen), `key_len`, `rows`, `filtered %`, `Extra`.

- Before indexing `product_name`: full scan of the whole table, ~10% filtered.
- After `CREATE INDEX ... ON products_1(product_name)`: index is used, rows scanned drops to ~589, no filtering needed.
- Adding a covering index (`product_name` + `isbn`) didn't change the plan — the optimizer chose by cost. Forcing it with `USE INDEX (...)` gave cost ~109 vs ~76 for the optimizer's pick, confirming the optimizer was right.

Takeaway: a covering index isn't automatically better; compare costs with `FORMAT=JSON`.
