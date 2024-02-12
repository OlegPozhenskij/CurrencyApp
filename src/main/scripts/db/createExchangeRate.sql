create schema "rates";

create table "rates"."exchange_rate" (
	 "id" BIGINT PRIMARY KEY,
     "local_date_time" TIMESTAMP,
     "rate_val" DECIMAL(19,4) not null,
     "currency_pair_id" BIGINT,
     FOREIGN KEY ("currency_pair_id") REFERENCES "currencies"."currency_pair"("id") ON DELETE RESTRICT
);
