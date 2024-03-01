CREATE SCHEMA if NOT EXISTS "currencies";
CREATE SCHEMA if NOT EXISTS "rates";

DROP TABLE IF EXISTS "currencies"."currency" CASCADE;
DROP TABLE IF EXISTS "currencies"."currency_pair" CASCADE;
DROP TABLE IF EXISTS "rates"."exchange_rate" CASCADE;


CREATE TABLE currencies.currency (
    id BIGSERIAL PRIMARY KEY,
    short_title VARCHAR(255) NOT NULL UNIQUE,
    full_title VARCHAR(255) NOT NULL
);

CREATE TABLE currencies.currency_pair (
    id BIGSERIAL PRIMARY KEY,
    precision INT,
    base_currency_id BIGINT,
    quoted_currency_id BIGINT,
    FOREIGN KEY (base_currency_id) REFERENCES currencies.currency(id) ON DELETE CASCADE,
    FOREIGN KEY (quoted_currency_id) REFERENCES currencies.currency(id) ON DELETE CASCADE
);

CREATE TABLE rates.exchange_rate (
	 id BIGSERIAL PRIMARY KEY,
     local_date_time TIMESTAMP,
     rate_val DECIMAL(19,4) NOT NULL,
     currency_pair_id BIGINT,
     FOREIGN KEY (currency_pair_id) REFERENCES currencies.currency_pair(id) ON DELETE RESTRICT
);



