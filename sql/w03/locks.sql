-- Table locks vs row locks
-- Run with THREE sessions. Lines are marked [SELLER] / [BUYER1] / [BUYER2].
-- Assumes the `products` table from isolation_levels.sql.

SET autocommit = 0;


-- ============================================================
-- The lost-update problem (why locks exist)
-- ============================================================
-- [SELLER] reads qty 40, plans to restock +60
SELECT qty FROM products WHERE product_id = 1;   -- 40

-- [BUYER1] reads qty 40 at the same time, buys 2
SELECT qty FROM products WHERE product_id = 1;   -- 40

-- [SELLER] writes 40 + 60
UPDATE products SET qty = 100 WHERE product_id = 1;
COMMIT;

-- [BUYER1] writes 40 - 2, computed from the stale read
UPDATE products SET qty = 38 WHERE product_id = 1;
COMMIT;

-- Final qty is 38: the seller's restock vanished.
SELECT qty FROM products WHERE product_id = 1;   -- 38


-- ============================================================
-- Table lock — concurrency collapses
-- ============================================================
-- [SELLER] locks the entire table for writing
LOCK TABLES products WRITE;
UPDATE products SET qty = qty + 60 WHERE product_id = 1;

-- [BUYER1] same book — hangs
UPDATE products SET qty = qty - 2 WHERE product_id = 1;

-- [BUYER2] a DIFFERENT book — also hangs, even though it's an unrelated row
UPDATE products SET qty = qty - 1 WHERE product_id = 2;

-- [BUYER2] even a plain browse hangs
SELECT * FROM products;

-- [SELLER] release; everyone else unblocks
COMMIT;
UNLOCK TABLES;


-- ============================================================
-- Row lock — InnoDB default, only the affected row is locked
-- ============================================================
-- [SELLER] FOR UPDATE takes an exclusive lock on row 1 only
START TRANSACTION;
SELECT qty FROM products WHERE product_id = 1 FOR UPDATE;
UPDATE products SET qty = qty + 60 WHERE product_id = 1;

-- [BUYER1] same row — blocks, then times out:
--   ERROR 1205 (HY000): Lock wait timeout exceeded; try restarting transaction
START TRANSACTION;
UPDATE products SET qty = qty - 2 WHERE product_id = 1;

-- [BUYER2] different row — succeeds immediately
START TRANSACTION;
UPDATE products SET qty = qty - 1 WHERE product_id = 2;
COMMIT;

-- [BUYER2] SELECT on any row still works (consistent read, no lock needed)
SELECT * FROM products;

-- How long a blocked session waits before erroring out (default 50s)
SHOW VARIABLES LIKE 'innodb_lock_wait_timeout';


-- ============================================================
-- Inspect the locks while SELLER's transaction is still open
-- ============================================================
-- Expect an IX (intention exclusive) row at TABLE level, plus a
-- RECORD lock on the specific key value (product_id = 1).
SELECT
    ENGINE_TRANSACTION_ID,
    OBJECT_NAME,
    INDEX_NAME,
    LOCK_TYPE,
    LOCK_MODE,
    LOCK_STATUS,
    LOCK_DATA
FROM performance_schema.data_locks;

-- [SELLER]
COMMIT;

SET autocommit = 1;
