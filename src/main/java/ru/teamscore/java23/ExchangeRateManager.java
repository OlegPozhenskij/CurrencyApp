package ru.teamscore.java23;

import jakarta.persistence.EntityManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.teamscore.java23.entities.Currency;
import ru.teamscore.java23.entities.ExchangeRate;

@RequiredArgsConstructor
public class ExchangeRateManager {

    private final EntityManager entityManager;

    //CRUD
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

    public void updateExchangeRate(ExchangeRate exchangeRate) {
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(exchangeRate);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Обработка ошибок при обновлении
        }
    }

    public void deleteExchangeRateById(long id) {
        var exchangeRate = entityManager.find(ExchangeRate.class, id);
        if (exchangeRate != null) {
            deleteExchangeRate(exchangeRate);
        }
    }

    private void deleteExchangeRate(ExchangeRate exchangeRate) {
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(exchangeRate);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Обработка ошибок при удалении
        }
    }

    public ExchangeRate getExchangeRateById(long id) {
        return entityManager.find(ExchangeRate.class, id);
    }
}
