package ru.teamscore.java23.entities;

import org.junit.jupiter.api.Test;
import ru.teamscore.java23.enums.Direction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PriceStatisticsTest {

    @Test
    void calcStats() {
        List<ExchangeRate> rates = new ArrayList<>();
        rates.add(new ExchangeRate(LocalDateTime.of(2024, 1, 1, 0, 0), BigDecimal.valueOf(1.1), null));
        rates.add(new ExchangeRate(LocalDateTime.of(2024, 1, 2, 0, 0), BigDecimal.valueOf(1.2), null));
        rates.add(new ExchangeRate(LocalDateTime.of(2024, 1, 3, 0, 0), BigDecimal.valueOf(1.3), null));
        rates.add(new ExchangeRate(LocalDateTime.of(2024, 1, 4, 0, 0), BigDecimal.valueOf(1.4), null));
        rates.add(new ExchangeRate(LocalDateTime.of(2024, 1, 5, 0, 0), BigDecimal.valueOf(1.5), null));

        PriceStatistics stats = PriceStatistics.calcStats(rates, LocalDateTime.of(2024, 1, 5, 0, 0));

        assertEquals(BigDecimal.valueOf(1.1), stats.getOpen());
        assertEquals(BigDecimal.valueOf(1.5), stats.getClose());
        assertEquals(BigDecimal.valueOf(1.5), stats.getMax());
        assertEquals(BigDecimal.valueOf(1.1), stats.getMin());
        assertEquals(Direction.UP, stats.getDirection());
        assertEquals(LocalDateTime.of(2024, 1, 5, 0, 0), stats.getTimestamp());
    }

}