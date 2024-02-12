CREATE SCHEMA "currencies";

CREATE TABLE "currencies"."currency" (
    "short_title" VARCHAR(5) PRIMARY KEY,
    "full_title" VARCHAR(20) NOT NULL
);

CREATE TABLE "currencies"."currency_pair" (
    "id" BIGINT PRIMARY KEY,
    "precision" INT NOT NULL DEFAULT 1,
    "base_currency_short_title" VARCHAR(255),
    "quoted_currency_short_title" VARCHAR(255),
    FOREIGN KEY ("base_currency_short_title")
        REFERENCES "currencies"."currency"("short_title")
        ON DELETE CASCADE,
    FOREIGN KEY ("quoted_currency_short_title")
        REFERENCES "currencies"."currency"("full_title")
        ON DELETE CASCADE
);


