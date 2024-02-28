-- RUN WITH postgres USER
CREATE SEQUENCE currency_id_seq START WITH 6;
CREATE SEQUENCE currency_pair_id_seq START WITH 5;
CREATE SEQUENCE exchange_rate_id_seq START WITH 5;
CREATE USER IF NOT EXISTS currencies_user WITH PASSWORD 'currencies';
CREATE DATABASE currencies OWNER currencies_user;