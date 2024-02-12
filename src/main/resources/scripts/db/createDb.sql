-- RUN WITH postgres USER
CREATE USER IF NOT EXISTS currencies_user WITH PASSWORD 'currencies';
CREATE DATABASE currencies OWNER currencies_user;