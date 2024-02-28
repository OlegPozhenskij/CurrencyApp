package ru.teamscore.java23.exceptions;

import lombok.Getter;
import ru.teamscore.java23.enums.Period;

public class CurrencyNotFoundException extends RuntimeException {
    public CurrencyNotFoundException(String message, String... curs) {
        super(message);
        System.out.println("Проблемы с поиском одной из этих валют: ");
        for (String s : curs) {
            System.out.print(s + " ");
        }
    }
}