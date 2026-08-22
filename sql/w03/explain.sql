-- EXPLAIN: reading the execution plan
-- Single session. `\G` formats the output vertically in the mysql client.
-- Uses products_1, a large copy of products so the plans differ visibly.

CREATE TABLE IF NOT EXISTS products_1 LIKE products;
ALTER TABLE products_1 DROP PRIMARY KEY;
INSERT INTO products_1 SELECT * FROM products;

-- Columns that matter:
--   type         ALL = full table scan (bad); ref/range = index used
--   possible_keys indexes the optimizer considered
--   key          the index actually chosen
--   key_len      how much of a multi-column index is used
--   rows         estimated rows examined
--   filtered     % of those rows expected to match (low = wasteful scan)
--   Extra        "Using where", "Using index" (covering index), etc.


-- ============================================================
-- Before indexing: full table scan
-- ============================================================
-- type: ALL, key: NULL, rows: whole table, filtered: ~10%
EXPLAIN
SELECT * FROM products_1
WHERE product_name = 'Clean Code'\G


-- ============================================================
-- After indexing product_name
-- ============================================================
CREATE INDEX product_name_idx
ON products_1 (product_name);

-- type: ref, key: product_name_idx, rows drops to ~589, filtered: 100%
EXPLAIN
SELECT * FROM products_1
WHERE product_name = 'Clean Code'\G


-- ============================================================
-- Covering index — not automatically better
-- ============================================================
CREATE INDEX product_name_isbn_idx
ON products_1 (product_name, isbn);

-- Plan is unchanged: the optimizer still picks product_name_idx, by cost.
EXPLAIN
SELECT product_name, isbn FROM products_1
WHERE product_name = 'Clean Code'\G

-- FORMAT=JSON adds "query_cost" — this is how to compare the two.
-- Optimizer's own pick: cost ~76
EXPLAIN FORMAT=JSON
SELECT product_name, isbn FROM products_1
WHERE product_name = 'Clean Code'\G

-- Force the covering index: cost ~109, i.e. worse. The optimizer was right.
EXPLAIN FORMAT=JSON
SELECT product_name, isbn FROM products_1
USE INDEX (product_name_isbn_idx)
WHERE product_name = 'Clean Code'\G


-- Takeaway: don't assume a covering index wins — compare query_cost.

SHOW INDEXES FROM products_1;
ALTER TABLE products_1 DROP INDEX product_name_isbn_idx;
