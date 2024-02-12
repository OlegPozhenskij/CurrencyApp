  ROLLBACK;
   -- Убедитесь, что начинаем транзакцию
   BEGIN;

   -- Удаляем все данные из таблицы Currency
   TRUNCATE TABLE "currencies"."currency" CASCADE;

   -- Вставляем тестовые данные в таблицу Currency
   INSERT INTO "currencies".currency (id, short_title, full_title) VALUES
   (1, 'USD', 'United States Dollar'),
   (2, 'EUR', 'Euro'),
   (3, 'GBP', 'British Pound Sterling'),
   (4, 'JPY', 'Japanese Yen'),
   (5, 'CAD', 'Canadian Dollar');

   -- Удаляем все данные из таблицы CurrencyPair
   TRUNCATE TABLE "currencies"."currency_pair" CASCADE;

   -- Вставляем тестовые данные в таблицу CurrencyPair
   INSERT INTO "currencies".currency_pair (id, precision, base_currency_short_title, quoted_currency_short_title) VALUES
   (1, 4, 'USD', 'EUR'),
   (2, 4, 'USD', 'GBP'),
   (3, 2, 'EUR', 'JPY'),
   (4, 3, 'EUR', 'CAD');

   -- Удаляем все данные из таблицы ExchangeRate
   TRUNCATE TABLE "rates"."exchange_rate" CASCADE;

   -- Вставляем тестовые данные в таблицу ExchangeRate
   -- Предполагается, что здесь будут вставлены реальные данные по курсам валют,
   -- но для целей примера, просто вставим фиктивные данные
   INSERT INTO "rates".exchange_rate (id, local_date_time, rate_val, currency_pair_id) VALUES
   (1, '2024-02-10 12:00:00', 0.85, 1),
   (2, '2024-02-11 13:00:00', 0.75, 2),
   (3, '2024-02-12 14:00:00', 130.25, 3),
   (4, '2024-02-13 15:00:00', 1.25, 4);

   -- Фиксируем транзакцию
   COMMIT;
