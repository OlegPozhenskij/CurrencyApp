CREATE SCHEMA if NOT EXISTS "currencies";
CREATE SCHEMA if NOT EXISTS "rates";

DROP TABLE IF EXISTS "currencies"."currency" CASCADE;
DROP TABLE IF EXISTS "currencies"."currency_pair" CASCADE;
DROP TABLE IF EXISTS "rates"."exchange_rate" CASCADE;


CREATE TABLE "currencies"."currency" (
    "id" BIGSERIAL PRIMARY KEY,
    "short_title" VARCHAR(255) NOT NULL UNIQUE,
    "full_title" VARCHAR(255) NOT NULL
);

CREATE TABLE "currencies"."currency_pair" (
    "id" BIGSERIAL PRIMARY KEY,
    "precision" INT NOT NULL DEFAULT 1,
    "base_currency_short_title" VARCHAR(255),
    "quoted_currency_short_title" VARCHAR(255),
    FOREIGN KEY ("base_currency_short_title")
        REFERENCES "currencies"."currency"("short_title")
        on delete cascade,
    FOREIGN KEY ("quoted_currency_short_title")
        REFERENCES "currencies"."currency"("short_title")
        on delete restrict
);

CREATE TABLE "rates"."exchange_rate" (
	 "id" BIGSERIAL PRIMARY KEY,
     "local_date_time" TIMESTAMP,
     "rate_val" DECIMAL(19,4) not null,
     "currency_pair_id" BIGINT,
     FOREIGN KEY ("currency_pair_id") REFERENCES "currencies"."currency_pair"("id") on delete restrict
);



