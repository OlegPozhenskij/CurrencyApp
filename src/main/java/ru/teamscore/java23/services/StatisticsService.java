package ru.teamscore.java23.services;

import ru.teamscore.java23.entities.CurrencyPair;
import ru.teamscore.java23.entities.PriceStatistics;
import ru.teamscore.java23.enums.Period;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class StatisticsService {

    public static List<PriceStatistics> getStats(CurrencyPair currencyPair, String startDate, String endDate, Period period) {
        //определение стартовой даты и конечной дат 20.09.00:00 - 20.10.00:00
        var startTimeLimit = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        var endTimeLimit =  LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        //определиться с периодом статистики 1hour
        var speedPeiodOfTime = startTimeLimit;
        var slowPeiodOfTime = startTimeLimit;

        //создадим List<PriceStatistics>, куда будем складывать по объекту статистики.
        var stats  = new ArrayList<PriceStatistics>();

        do {
            //на основе стартовой даты вычислаем ластовую дату и сохраняем в стартовую (Периода 20.09.00:00 + 1hour = 20.09.01:00)
            speedPeiodOfTime = speedPeiodOfTime.plus(1, period.getChronoUnit());

            //составляем list<exRate> на основе этого промежутка
            var exRateList = currencyPair.getExchangeRates(slowPeiodOfTime, speedPeiodOfTime);

            slowPeiodOfTime = slowPeiodOfTime.plus(1, period.getChronoUnit());

            //отправляем в calcStats(лист, предыдущую статистику(либо null), вычесленую ластовую дату);
            //Сохраняем в лист List<PriceStatistics>
            if (!exRateList.isEmpty()) {
                stats.add(PriceStatistics.calcStats(exRateList,
                        stats.isEmpty() ? null : stats.get(stats.size() - 1),
                        speedPeiodOfTime
                ));
            }
        }
        while (speedPeiodOfTime.isBefore(endTimeLimit) || speedPeiodOfTime.isEqual(endTimeLimit));

        return stats.stream().distinct().toList();
    }

    public static List<PriceStatistics> getStats(CurrencyPair currencyPair, int numOfPoints, Period period) {
        //Выбрать кол-во записей exchangeRate 100 (1 запись в минуту) List<exchangeRate>
        var rates = currencyPair.getExchangeRates(numOfPoints);

        //создадим List<PriceStatistics>, куда будем складывать по объекту статистики.
        var stats  = new ArrayList<PriceStatistics>();

        //определение стартовой даты, как дата данного момента
        var fastDate = clockSetup(LocalDateTime.now(), period.getChronoUnit());
        var slowDate = fastDate;

        do {
            //на основе стартовой даты вычислаем более ранную дату Периода и сохраняем в стартовую (Данный момент(20.09.19:30) - 15 мин = 20.09.19:15)
            fastDate = fastDate.minus(1, period.getChronoUnit());
            LocalDateTime finalFastDate = fastDate;
            LocalDateTime finalSlowDate = slowDate;

            //составляем list<exRate> на основе этого промежутка и находим что в этом промежутке 15 записей
            var exRateList = rates
                    .stream()
                    .filter(exchangeRate -> (
                            exchangeRate.getLocalDateTime().isAfter(finalFastDate)
                    ))
                    .filter(exchangeRate -> (
                            exchangeRate.getLocalDateTime().isBefore(finalSlowDate) ||
                                    exchangeRate.getLocalDateTime().isEqual(finalSlowDate)
                    ))
                    .toList();

            if (!exRateList.isEmpty()) {
                stats.add(PriceStatistics.calcStats(exRateList,
                        stats.isEmpty() ? null : stats.get(stats.size() - 1),
                        slowDate
                ));
                rates.removeAll(exRateList);
            }
            slowDate = slowDate.minus(1, period.getChronoUnit());
        }
        while (!rates.isEmpty());

        //дропнули дубликаты
        return stats.stream().distinct().toList();
    }

    private static LocalDateTime clockSetup(LocalDateTime dateTime, ChronoUnit chronoUnit) {
        LocalDate truncatedDate;
        if (chronoUnit == ChronoUnit.MONTHS || chronoUnit == ChronoUnit.YEARS) {
            truncatedDate = dateTime.toLocalDate().plus(1, chronoUnit)
                    .withDayOfMonth(1);
        } else {
            return dateTime.truncatedTo(chronoUnit);
        }
        return truncatedDate.atStartOfDay();
    }

}
