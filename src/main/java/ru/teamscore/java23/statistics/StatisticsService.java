package ru.teamscore.java23.statistics;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.teamscore.java23.models.CurrencyPair;
import ru.teamscore.java23.models.ExchangeRate;
import ru.teamscore.java23.models.PriceStatistics;
import ru.teamscore.java23.enums.DateSide;
import ru.teamscore.java23.enums.Period;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class StatisticsService {

    public static List<PriceStatistics> getStats(EntityManager entityManager, CurrencyPair currencyPair, LocalDateTime startDate, LocalDateTime endDate, Period period) {
        List<PriceStatistics> stats = new ArrayList<>();

        var currentPeriodStart = clockSetup(startDate, period.getChronoUnit(), DateSide.LEFT);

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
                        currentPeriodEnd
                ));
            }
        }

        return stats;
    }

    public static List<PriceStatistics> getStats(EntityManager entityManager, CurrencyPair currencyPair, int numOfPoints, Period period) {
        List<PriceStatistics> stats = new ArrayList<>();

        Query<ExchangeRate> query = (Query<ExchangeRate>) entityManager.createQuery(
                "SELECT er FROM ExchangeRate er WHERE er.currencyPair = :currencyPair ORDER BY er.localDateTime DESC",
                ExchangeRate.class);
        query.setParameter("currencyPair", currencyPair);
        query.setMaxResults(numOfPoints);
        List<ExchangeRate> rates = query.getResultList();

        if (rates.isEmpty()) { return stats; }

        var currentPeriodStart = clockSetup(rates.get(0).getLocalDateTime(), period.getChronoUnit(), DateSide.RIGHT);

        do {
            var currentPeriodEnd = currentPeriodStart.minus(1, period.getChronoUnit());

            LocalDateTime finalCurrentPeriodStart = currentPeriodStart;
            List<ExchangeRate> exRateList = rates.stream()
                    .filter(exchangeRate -> exchangeRate.getLocalDateTime().isAfter(currentPeriodEnd))
                    .filter(exchangeRate -> exchangeRate.getLocalDateTime().isBefore(finalCurrentPeriodStart) || exchangeRate.getLocalDateTime().isEqual(finalCurrentPeriodStart))
                    .toList();

            if (!exRateList.isEmpty()) {
                stats.add(PriceStatistics.calcStats(exRateList,
                        currentPeriodStart
                ));
                rates.removeAll(exRateList);
            }
            currentPeriodStart = currentPeriodEnd;
        }
        while (!rates.isEmpty());

        return stats.stream().toList();
    }

    private static LocalDateTime clockSetup(LocalDateTime dateTime, ChronoUnit chronoUnit, DateSide side) {
        LocalDate truncatedDate;
        if (chronoUnit == ChronoUnit.MONTHS || chronoUnit == ChronoUnit.YEARS) {
            truncatedDate = dateTime.toLocalDate().plus(1, chronoUnit)
                    .withDayOfMonth(1);
        } else {
            return (side.equals(DateSide.RIGHT))
                    ? dateTime.plus(1, chronoUnit).truncatedTo(chronoUnit)
                    : dateTime.truncatedTo(chronoUnit);
        }
        return (side.equals(DateSide.RIGHT))
                ? truncatedDate.plus(1, chronoUnit).atStartOfDay()
                : truncatedDate.atStartOfDay();
    }

}
