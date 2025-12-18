USE telecom_db;

INSERT INTO plans (name, data_limit_gb, call_limit_minutes, monthly_fee) VALUES
('Basic Plan', 10, 100, 29.99),
('Premium Plan', 50, 500, 59.99);

INSERT INTO customers (name, email) VALUES ('John Doe', 'john@example.com');

INSERT INTO customer_plans (customer_id, plan_id) VALUES (1, 1);

INSERT INTO usage_records (customer_id, month_year, data_used_gb, calls_used_minutes) VALUES
(1, '2023-10-01', 12.5, 120);