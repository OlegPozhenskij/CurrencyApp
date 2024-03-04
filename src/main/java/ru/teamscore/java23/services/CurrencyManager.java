package ru.teamscore.java23.services;

import jakarta.persistence.EntityManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.teamscore.java23.entities.Currency;
import ru.teamscore.java23.entities.CurrencyPair;

import java.util.List;

@RequiredArgsConstructor
public class CurrencyManager {

    private final EntityManager entityManager;

    //CRUD
    public void saveCurrency(Currency currency) {
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(currency);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void updateCurrency(Currency currency) {
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(currency);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void deleteCurrencyPair(@NonNull Currency currency) {
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(entityManager.contains(currency)
                    ? currency
                    : entityManager.merge(currency));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // DEL ex
        }
    }

    public Currency getCurrencyByShortTitle(String shortTitle) {
        return entityManager
                .createNamedQuery("currencyByShortTitle", Currency.class)
                .setParameter("shortTitle", shortTitle)
                .getSingleResult();
    }

    public List<Currency> getAllCurrencies() {
        return entityManager
                .createNamedQuery("currenciesCount", Currency.class)
                .getResultList();
    }

    public Currency getCurrencyById(long id) {
        return entityManager.find(Currency.class, id);
    }

    public long countCurrencies() {
        return (Long) entityManager
                .createNamedQuery("currenciesCount", Long.class)
                .getSingleResult();
    }

    public List<Currency> getCurrenciesByNameSubstring(String nameSubstring) {
        return entityManager.createQuery("SELECT c FROM Currency c WHERE c.fullTitle LIKE :nameSubstring", Currency.class)
                .setParameter("nameSubstring", "%" + nameSubstring + "%")
                .getResultList();
    }
}
