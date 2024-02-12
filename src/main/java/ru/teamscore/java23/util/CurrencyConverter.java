package ru.teamscore.java23.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import ru.teamscore.java23.entities.Currency;

@RequiredArgsConstructor
public class CurrencyConverter implements AttributeConverter<Currency, String> {
    private final EntityManager entityManager;

    @Override
    public String convertToDatabaseColumn(Currency currency) {
        return currency.getShortTitle();
    }

    @Override
    public Currency convertToEntityAttribute(String s) {
        return (Currency) entityManager
                .createNamedQuery("currencyByShortTitle")
                .setParameter("short_title", s);
    }
}
