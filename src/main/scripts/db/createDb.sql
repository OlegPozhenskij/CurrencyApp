-- RUN WITH postgres USER
CREATE USER currencies_user WITH PASSWORD 'currencies';
CREATE DATABASE currencies OWNER currencies_user;