package ru.teamscore.java23.models.services;

import jakarta.persistence.EntityManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.teamscore.java23.models.Currency;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyManager {

    @Autowired
    private final EntityManager entityManager;

    public void saveOrUpdateCurrency(Currency currency) {
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (currency.getId() == 0) {
                entityManager.persist(currency);
            } else {
                entityManager.merge(currency);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }


    public void deleteCurrency(@NonNull Currency currency) {
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
            throw e;
        }
    }

    public Currency getCurrencyByShortTitle(String shortTitle) {
        return entityManager
                .createNamedQuery("currencyByShortTitle", Currency.class)
                .setParameter("shortTitle", shortTitle)
                .getSingleResult();
    }

    public List<Currency> getAllCurrencies() {
        return entityManager.createQuery("SELECT c FROM Currency c ", Currency.class)
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
