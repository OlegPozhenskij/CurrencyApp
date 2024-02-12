package ru.teamscore.java23;

import jakarta.persistence.EntityManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.teamscore.java23.entities.Currency;
import ru.teamscore.java23.entities.CurrencyPair;

@RequiredArgsConstructor
public class CurrencyManager {

    private final EntityManager entityManager;

    public void saveCurrency(@NonNull Currency currency) {
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(currency);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // ADD ex
        }
    }

    public void deleteCurrencyPairById(@NonNull Currency currency) {
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
}
