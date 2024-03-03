package ru.teamscore.java23.entities;

import lombok.*;
import ru.teamscore.java23.enums.Direction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PriceStatistics {
    private LocalDateTime timestamp;
    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal max;
    private BigDecimal min;
    private Direction direction;

    public static PriceStatistics calcStats(List<ExchangeRate> rates, LocalDateTime dateTime) {
        var open = findOpen(rates);
        var close = findClose(rates);

        return new PriceStatistics(
                dateTime,
                open,
                close,
                findMax(rates),
                findMin(rates),
                chooseDirection(open, close)
        );
    }

    private static Direction chooseDirection(BigDecimal open, BigDecimal close) {
        if (open == null || close == null) return Direction.NONE;

        if (open.doubleValue() < close.doubleValue()) {
            return Direction.UP;
        } else if (open.doubleValue() > close.doubleValue()) {
            return Direction.DOWN;
        } else {
            return Direction.NONE;
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
