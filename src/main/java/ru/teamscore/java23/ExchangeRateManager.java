package ru.teamscore.java23;

import jakarta.persistence.EntityManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.teamscore.java23.entities.Currency;
import ru.teamscore.java23.entities.ExchangeRate;

@RequiredArgsConstructor
public class ExchangeRateManager {

    private final EntityManager entityManager;

    public void saveCurrency(@NonNull ExchangeRate exchangeRate) {
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(exchangeRate);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // ADD ex
        }
    }

    public void deleteCurrencyPairById(@NonNull ExchangeRate exchangeRate) {
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(entityManager.contains(exchangeRate)
                    ? exchangeRate
                    : entityManager.merge(exchangeRate));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // DEL ex
        }
    }
}
