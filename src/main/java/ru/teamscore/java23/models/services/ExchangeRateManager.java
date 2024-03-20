package ru.teamscore.java23.models.services;

import jakarta.persistence.EntityManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.teamscore.java23.models.ExchangeRate;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ExchangeRateManager {

    @Autowired
    private final EntityManager entityManager;

    public void saveRate(@NonNull ExchangeRate exchangeRate) {
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(exchangeRate);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public List getAllExchangeRates() {
        return entityManager.createQuery("SELECT e FROM ExchangeRate e", ExchangeRate.class).getResultList();
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
            throw e;
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
            throw e;
        }
    }

    public ExchangeRate getExchangeRateById(long id) {
        return entityManager.find(ExchangeRate.class, id);
    }
}
