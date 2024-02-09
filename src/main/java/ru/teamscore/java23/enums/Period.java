package ru.teamscore.java23.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.temporal.ChronoUnit;


public enum Period {
    MINUTE(ChronoUnit.MINUTES),
    HOUR(ChronoUnit.HOURS),
    DAY(ChronoUnit.DAYS),
    MONTH(ChronoUnit.MONTHS),
    YEAR(ChronoUnit.YEARS);

    @Getter
    private final ChronoUnit chronoUnit;

    Period(ChronoUnit chronoUnit) {this.chronoUnit = chronoUnit;}
}
