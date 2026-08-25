-- Deadlocks: two transactions each holding a lock the other needs
-- Run with TWO sessions, in the numbered order. Lines are marked [A] / [B].
-- Assumes the `products` table from isolation_levels.sql.

SET autocommit = 0;


-- 1. [A] locks row 1
START TRANSACTION;
UPDATE products SET qty = qty + 1 WHERE product_id = 1;

-- 2. [B] locks row 2 — no conflict yet, independent rows
START TRANSACTION;
UPDATE products SET qty = qty + 1 WHERE product_id = 2;

-- 3. [B] now wants row 1, which A holds → B waits
UPDATE products SET qty = qty + 1 WHERE product_id = 1;

-- 4. [A] now wants row 2, which B holds → circular wait
--    InnoDB detects it and kills one session as the victim:
--      ERROR 1213 (40001): Deadlock found when trying to get lock;
--      try restarting transaction
--    The victim's transaction is rolled back; the other one proceeds.
UPDATE products SET qty = qty + 1 WHERE product_id = 2;

-- 5. Whichever session survived
COMMIT;

-- 6. The victim retries its work from the start
START TRANSACTION;
UPDATE products SET qty = qty + 1 WHERE product_id = 1;
UPDATE products SET qty = qty + 1 WHERE product_id = 2;
COMMIT;


-- Details of the most recent deadlock
SHOW ENGINE INNODB STATUS;


-- Prevention: keep transactions short, and always touch rows in a
-- consistent order (here: ascending product_id in BOTH sessions, so
-- neither can be holding a row the other needs first).
START TRANSACTION;
UPDATE products SET qty = qty + 1 WHERE product_id = 1;
UPDATE products SET qty = qty + 1 WHERE product_id = 2;
COMMIT;

SET autocommit = 1;
