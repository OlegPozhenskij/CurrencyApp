package ru.teamscore.java23.exceptions;

import lombok.Getter;
import ru.teamscore.java23.enums.Period;

public class PeriodException extends RuntimeException {
    @Getter
    private Period oldStatus;
    @Getter
    private Period newStatus;

    public PeriodException(String message, Period oldStatus, Period newStatus) {
        super(message);
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }
}