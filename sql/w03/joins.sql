-- INNER JOIN
SELECT transaction_id, amount, first_name, last_name
FROM transactions
INNER JOIN customers
ON transactions.customer_id = customers.customer_id;

-- LEFT JOIN
SELECT transaction_id, amount, first_name, last_name
FROM transactions
LEFT JOIN customers
ON transactions.customer_id = customers.customer_id;

-- RIGHT JOIN
SELECT transaction_id, amount, first_name, last_name
FROM transactions
RIGHT JOIN customers
ON transactions.customer_id = customers.customer_id;

-- SELF JOIN setup
ALTER TABLE customers ADD referral_id INT;

-- Larry Lobster (id 2) referred by Fred Fish (id 1)
UPDATE customers SET referral_id = 1 WHERE customer_id = 2;

-- Bubble Bass (id 3) and Poppy Puff (id 4) referred by Larry Lobster (id 2)
UPDATE customers SET referral_id = 2 WHERE customer_id IN (3, 4);

-- SELF JOIN (INNER)
SELECT
    a.customer_id,
    a.first_name,
    a.last_name,
    CONCAT(b.first_name, ' ', b.last_name) AS referred_by
FROM customers AS a
INNER JOIN customers AS b
ON a.referral_id = b.customer_id;

-- SELF JOIN (LEFT JOIN variant, includes customers with no referral)
SELECT
    a.customer_id,
    a.first_name,
    a.last_name,
    CONCAT(b.first_name, ' ', b.last_name) AS referred_by
FROM customers AS a
LEFT JOIN customers AS b
ON a.referral_id = b.customer_id;
