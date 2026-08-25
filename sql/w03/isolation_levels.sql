-- Transaction isolation levels
-- Run with TWO sessions side by side. Lines are marked [A] / [B].

-- Setup (either session)
CREATE TABLE IF NOT EXISTS products (
    product_id   INT PRIMARY KEY,
    product_name VARCHAR(100),
    isbn         VARCHAR(20),
    qty          INT
);

INSERT INTO products VALUES
    (1, 'Clean Code', '9780132350884', 40),
    (2, 'The Pragmatic Programmer', '9780135957059', 25),
    (3, 'Effective Java', '9780134685991', 30);

-- Check the current level (MySQL default is REPEATABLE READ)
SHOW VARIABLES LIKE '%isolation%';

-- Commits must be explicit for these demos
SET autocommit = 0;


-- ============================================================
-- READ UNCOMMITTED — dirty reads
-- ============================================================
-- [A]
SET SESSION TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
START TRANSACTION;
SELECT qty FROM products WHERE product_id = 1;   -- 40

-- [B]
START TRANSACTION;
UPDATE products SET qty = 100 WHERE product_id = 1;
-- no COMMIT yet

-- [A] re-read: sees 100 even though B never committed = dirty read
SELECT qty FROM products WHERE product_id = 1;   -- 100

-- [B]
ROLLBACK;

-- [A] the value A read never actually existed
SELECT qty FROM products WHERE product_id = 1;   -- 40
COMMIT;


-- ============================================================
-- READ COMMITTED — only committed data, recommended for OLTP
-- ============================================================
-- [A]
SET SESSION TRANSACTION ISOLATION LEVEL READ COMMITTED;
START TRANSACTION;
SELECT qty FROM products WHERE product_id = 1;   -- 40

-- [B]
START TRANSACTION;
UPDATE products SET qty = 100 WHERE product_id = 1;

-- [A] still 40 — B has not committed
SELECT qty FROM products WHERE product_id = 1;   -- 40

-- [B]
COMMIT;

-- [A] now sees the committed value (non-repeatable read)
SELECT qty FROM products WHERE product_id = 1;   -- 100
COMMIT;


-- ============================================================
-- REPEATABLE READ — MySQL's default
-- ============================================================
-- [A]
SET SESSION TRANSACTION ISOLATION LEVEL REPEATABLE READ;
START TRANSACTION;
SELECT qty FROM products WHERE product_id = 1;   -- 100

-- [B]
START TRANSACTION;
UPDATE products SET qty = 55 WHERE product_id = 1;
COMMIT;

-- [A] still 100 — repeated reads inside the transaction are stable
SELECT qty FROM products WHERE product_id = 1;   -- 100
COMMIT;

-- [A] after committing, the new snapshot shows B's change
SELECT qty FROM products WHERE product_id = 1;   -- 55


-- ============================================================
-- SERIALIZABLE — strictest; a plain SELECT takes a lock
-- ============================================================
-- [A]
SET SESSION TRANSACTION ISOLATION LEVEL SERIALIZABLE;
START TRANSACTION;
SELECT qty FROM products WHERE product_id = 1;   -- locks the row

-- [B] blocks until A commits, or fails with:
--   ERROR 1205 (HY000): Lock wait timeout exceeded; try restarting transaction
START TRANSACTION;
UPDATE products SET qty = 10 WHERE product_id = 1;

-- [A]
COMMIT;   -- B's update can now proceed

-- Reset
SET SESSION TRANSACTION ISOLATION LEVEL REPEATABLE READ;
SET autocommit = 1;
