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

        var currentPeriodStart = clockSetup(startDate, period.getChronoUnit());;

        while (!currentPeriodStart.isAfter(endDate)) {
            var currentPeriodEnd = currentPeriodStart.plus(1, period.getChronoUnit());

            Query<ExchangeRate> query = (Query<ExchangeRate>) entityManager.createQuery(
                    "SELECT er FROM ExchangeRate er WHERE er.currencyPair = :currencyPair AND er.localDateTime >= :startTime AND er.localDateTime < :endTime",
                    ExchangeRate.class);
            query.setParameter("currencyPair", currencyPair);
            query.setParameter("startTime", currentPeriodStart);
            query.setParameter("endTime", currentPeriodEnd);
            List<ExchangeRate> exchangeRates = query.getResultList();

            currentPeriodStart = currentPeriodEnd;

            if (!exchangeRates.isEmpty()) {
                stats.add(PriceStatistics.calcStats(exchangeRates,
                        stats.isEmpty() ? null : stats.get(stats.size() - 1),
                        currentPeriodEnd
                ));
            }
        }

        return stats;
    }

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

        var currentPeriodStart = clockSetup(rates.get(0).getLocalDateTime(), period.getChronoUnit());

        do {
            var currentPeriodEnd = currentPeriodStart.minus(1, period.getChronoUnit());

            LocalDateTime finalCurrentPeriodStart = currentPeriodStart;
            List<ExchangeRate> exRateList = rates.stream()
                    .filter(exchangeRate -> exchangeRate.getLocalDateTime().isAfter(currentPeriodEnd))
                    .filter(exchangeRate -> exchangeRate.getLocalDateTime().isBefore(finalCurrentPeriodStart) || exchangeRate.getLocalDateTime().isEqual(finalCurrentPeriodStart))
                    .toList();

            if (!exRateList.isEmpty()) {
                stats.add(PriceStatistics.calcStats(exRateList,
                        stats.isEmpty() ? null : stats.get(stats.size() - 1),
                        currentPeriodStart
                ));
                rates.removeAll(exRateList);
            }
            currentPeriodStart = currentPeriodEnd;
        }
        while (!rates.isEmpty());

        return stats.stream().toList();
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
