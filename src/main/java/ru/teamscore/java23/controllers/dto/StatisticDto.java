package ru.teamscore.java23.controllers.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.teamscore.java23.models.PriceStatistics;
import ru.teamscore.java23.models.enums.Direction;

import java.math.BigDecimal;
import java.time.ZoneOffset;

@Data
@NoArgsConstructor
public class StatisticDto {
    private Long timestamp;
    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal max;
    private BigDecimal min;
    private Direction direction;

    public StatisticDto(PriceStatistics ps) {
        this.timestamp = ps.getTimestamp().toInstant(ZoneOffset.UTC).toEpochMilli();
        this.open = ps.getOpen();
        this.close = ps.getClose();
        this.max = ps.getMax();
        this.min = ps.getMin();
        this.direction = ps.getDirection();
    }
}
