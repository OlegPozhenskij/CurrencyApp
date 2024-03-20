package ru.teamscore.java23.models.enums;

import lombok.Getter;

import java.time.temporal.ChronoUnit;


@Getter
public enum Period {
    MINUTE(ChronoUnit.MINUTES),
    HOUR(ChronoUnit.HOURS),
    DAY(ChronoUnit.DAYS),
    MONTH(ChronoUnit.MONTHS),
    YEAR(ChronoUnit.YEARS);

    private final ChronoUnit chronoUnit;

    Period(ChronoUnit chronoUnit) {this.chronoUnit = chronoUnit;}
}
