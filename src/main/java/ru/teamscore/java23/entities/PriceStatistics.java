package ru.teamscore.java23.entities;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PriceStatistics {
    private LocalDateTime timestamp; // как вычислять это (начало или конец)
    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal max;
    private BigDecimal min;
    private char direction; // как вычислять (относительно предыдущего?)

    public static PriceStatistics calcStats(List<ExchangeRate> rates, @NonNull PriceStatistics statistic, LocalDateTime dateTime) {
        return new PriceStatistics(
                dateTime,
                findOpen(rates),
                findClose(rates),
                findMax(rates),
                findMin(rates),
                chooseDirection(statistic, rates)
        );
    }

    private static char chooseDirection(@NonNull PriceStatistics s, List<ExchangeRate> rates) {
        var res = findMax(rates).compareTo(s.close);
        if (res > 0) {
            return '+';
        } else if(res < 0) {
            return '-';
        } else {
            return '=';
        }
    }

    private static BigDecimal findMin(List<ExchangeRate> rates) {
        return rates.stream().min(Comparator.comparing(ExchangeRate::getRateVal)).get().getRateVal();
    }

    private static BigDecimal findMax(List<ExchangeRate> rates) {
        return rates.stream().max(Comparator.comparing(ExchangeRate::getRateVal)).get().getRateVal();
    }

    private static BigDecimal findClose(List<ExchangeRate> rates) {
        return rates.stream().max(Comparator.comparing(ExchangeRate::getLocalDateTime)).get().getRateVal();
    }

    private static BigDecimal findOpen(List<ExchangeRate> rates) {
        return rates.stream().min(Comparator.comparing(ExchangeRate::getLocalDateTime)).get().getRateVal();
    }
}
