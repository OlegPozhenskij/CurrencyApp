package ru.teamscore.java23.services;

import jakarta.persistence.*;
import org.hibernate.cfg.Configuration;
import ru.teamscore.java23.entities.Currency;
import ru.teamscore.java23.entities.CurrencyPair;
import ru.teamscore.java23.entities.ExchangeRate;


public class CurrencyConverter implements AttributeConverter<Currency, String> {
    @Override
    public String convertToDatabaseColumn(Currency currency) {
        return currency.getShortTitle();
    }

    //Возможно ли как то прокинуть сюда EntityManager, более простым способом?
    //память на объекты кушается, вынес в отдельную переменную создание Factory и EntityManager
    //в итоге всё сломалось, оставил то что работает.
    @Override
    public Currency convertToEntityAttribute(String s) {
        var factory = new Configuration()
                .configure("hibernate-postgres.cfg.xml")
                .addAnnotatedClass(CurrencyPair.class)
                .addAnnotatedClass(Currency.class)
                .addAnnotatedClass(ExchangeRate.class)
                .buildSessionFactory();

        Query q = factory.createEntityManager()
                .createNamedQuery("currencyByShortTitle")
                .setParameter("shortTitle", s);

        return (Currency) q.getSingleResult();
    }
}
