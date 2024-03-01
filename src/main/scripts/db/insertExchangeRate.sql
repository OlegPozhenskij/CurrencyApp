-- Предположим, что currency_pair_id для USD to EUR равен 1, а для EUR to USD равен 2
INSERT INTO rates.exchange_rate (local_date_time, rate_val, currency_pair_id) VALUES
('2023-01-01 00:00:00', 0.8524, 1),
('2023-01-01 00:00:00', 1.1823, 2);