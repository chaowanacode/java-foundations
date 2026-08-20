-- View current indexes
SHOW INDEXES FROM customers;

-- Single-column index
CREATE INDEX last_name_idx
ON customers (last_name);

SHOW INDEXES FROM customers;

-- Search using the index
SELECT * FROM customers
WHERE last_name = 'Bass';

-- Multi-column index (leftmost prefix: last_name must come first)
CREATE INDEX last_name_first_name_idx
ON customers (last_name, first_name);

SHOW INDEXES FROM customers;

-- Drop the now-redundant single-column index
ALTER TABLE customers DROP INDEX last_name_idx;

SHOW INDEXES FROM customers;
