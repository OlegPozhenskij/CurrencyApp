package ru.teamscore.java23.statistics;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import ru.teamscore.java23.entities.CurrencyPair;
import ru.teamscore.java23.entities.ExchangeRate;
import ru.teamscore.java23.entities.PriceStatistics;
import ru.teamscore.java23.enums.Period;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class StatisticsService {

    private final EntityManager manager;

    public static List<PriceStatistics> getStats(EntityManager entityManager, CurrencyPair currencyPair, LocalDateTime startDate, LocalDateTime endDate, Period period) {
        List<PriceStatistics> stats = new ArrayList<>();

        LocalDateTime speedPeriodOfTime = startDate;
        LocalDateTime slowPeriodOfTime = startDate;

        while (!speedPeriodOfTime.isAfter(endDate)) {
            speedPeriodOfTime = speedPeriodOfTime.plus(1, period.getChronoUnit());

            Query<ExchangeRate> query = (Query<ExchangeRate>) entityManager.createQuery(
                    "SELECT er FROM ExchangeRate er WHERE er.currencyPair = :currencyPair AND er.localDateTime >= :startTime AND er.localDateTime < :endTime",
                    ExchangeRate.class);
            query.setParameter("currencyPair", currencyPair);
            query.setParameter("startTime", slowPeriodOfTime);
            query.setParameter("endTime", speedPeriodOfTime);
            List<ExchangeRate> exchangeRates = query.getResultList();

            slowPeriodOfTime = slowPeriodOfTime.plus(1, period.getChronoUnit());

            if (!exchangeRates.isEmpty()) {
                stats.add(PriceStatistics.calcStats(exchangeRates,
                        stats.isEmpty() ? null : stats.get(stats.size() - 1),
                        speedPeriodOfTime
                ));
            }
        }

        return stats;
    }
//    public static List<PriceStatistics> getStats(CurrencyPair currencyPair, String startDate, String endDate, Period period) {
//        //определение стартовой даты и конечной дат 20.09.00:00 - 20.10.00:00
//        var startTimeLimit = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
//        var endTimeLimit =  LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
//
//        //определиться с периодом статистики 1hour
//        var speedPeiodOfTime = startTimeLimit;
//        var slowPeiodOfTime = startTimeLimit;
//
//        //создадим List<PriceStatistics>, куда будем складывать по объекту статистики.
//        var stats  = new ArrayList<PriceStatistics>();
//
//        do {
//            //на основе стартовой даты вычислаем ластовую дату и сохраняем в стартовую (Периода 20.09.00:00 + 1hour = 20.09.01:00)
//            speedPeiodOfTime = speedPeiodOfTime.plus(1, period.getChronoUnit());
//
//            //составляем list<exRate> на основе этого промежутка
//            var exRateList = currencyPair.getExchangeRates(slowPeiodOfTime, speedPeiodOfTime);
//
//            slowPeiodOfTime = slowPeiodOfTime.plus(1, period.getChronoUnit());
//
//            //отправляем в calcStats(лист, предыдущую статистику(либо null), вычесленую ластовую дату);
//            //Сохраняем в лист List<PriceStatistics>
//            if (!exRateList.isEmpty()) {
//                stats.add(PriceStatistics.calcStats(exRateList,
//                        stats.isEmpty() ? null : stats.get(stats.size() - 1),
//                        speedPeiodOfTime
//                ));
//            }
//        }
//        while (speedPeiodOfTime.isBefore(endTimeLimit) || speedPeiodOfTime.isEqual(endTimeLimit));
//
//        return stats.stream().distinct().toList();
//    }

    public static List<PriceStatistics> getStats(EntityManager entityManager, CurrencyPair currencyPair, int numOfPoints, Period period) {
        List<PriceStatistics> stats = new ArrayList<>();

        // Fetch a specified number of recent exchange rates from the database
        Query<ExchangeRate> query = (Query<ExchangeRate>) entityManager.createQuery(
                "SELECT er FROM ExchangeRate er WHERE er.currencyPair = :currencyPair ORDER BY er.localDateTime DESC",
                ExchangeRate.class);
        query.setParameter("currencyPair", currencyPair);
        query.setMaxResults(numOfPoints);
        List<ExchangeRate> rates = query.getResultList();

        if (rates.isEmpty()) { return stats; }

        LocalDateTime fastDate = clockSetup(rates.get(0).getLocalDateTime(), period.getChronoUnit());
        LocalDateTime slowDate = fastDate;

        do {
            fastDate = fastDate.minus(1, period.getChronoUnit());
            LocalDateTime finalFastDate = fastDate;
            LocalDateTime finalSlowDate = slowDate;

            List<ExchangeRate> exRateList = rates.stream()
                    .filter(exchangeRate -> exchangeRate.getLocalDateTime().isAfter(finalFastDate))
                    .filter(exchangeRate -> exchangeRate.getLocalDateTime().isBefore(finalSlowDate) || exchangeRate.getLocalDateTime().isEqual(finalSlowDate))
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

        return stats.stream().distinct().toList();
    }

//    public static List<PriceStatistics> getStats(CurrencyPair currencyPair, int numOfPoints, Period period) {
//        //Выбрать кол-во записей exchangeRate 100 (1 запись в минуту) List<exchangeRate>
//        var rates = currencyPair.getExchangeRates(numOfPoints);
//
//        //создадим List<PriceStatistics>, куда будем складывать по объекту статистики.
//        var stats  = new ArrayList<PriceStatistics>();
//
//        //определение стартовой даты, как дата данного момента
//        var fastDate = clockSetup(LocalDateTime.now(), period.getChronoUnit());
//        var slowDate = fastDate;
//
//        do {
//            //на основе стартовой даты вычислаем более ранную дату Периода и сохраняем в стартовую (Данный момент(20.09.19:30) - 15 мин = 20.09.19:15)
//            fastDate = fastDate.minus(1, period.getChronoUnit());
//            LocalDateTime finalFastDate = fastDate;
//            LocalDateTime finalSlowDate = slowDate;
//
//            //составляем list<exRate> на основе этого промежутка и находим что в этом промежутке 15 записей
//            var exRateList = rates
//                    .stream()
//                    .filter(exchangeRate -> (
//                            exchangeRate.getLocalDateTime().isAfter(finalFastDate)
//                    ))
//                    .filter(exchangeRate -> (
//                            exchangeRate.getLocalDateTime().isBefore(finalSlowDate) ||
//                                    exchangeRate.getLocalDateTime().isEqual(finalSlowDate)
//                    ))
//                    .toList();
//
//            if (!exRateList.isEmpty()) {
//                stats.add(PriceStatistics.calcStats(exRateList,
//                        stats.isEmpty() ? null : stats.get(stats.size() - 1),
//                        slowDate
//                ));
//                rates.removeAll(exRateList);
//            }
//            slowDate = slowDate.minus(1, period.getChronoUnit());
//        }
//        while (!rates.isEmpty());
//
//        //дропнули дубликаты
//        return stats.stream().distinct().toList();
//    }

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
