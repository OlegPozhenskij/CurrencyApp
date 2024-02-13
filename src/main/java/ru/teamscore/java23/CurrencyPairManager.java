package ru.teamscore.java23;

import jakarta.persistence.EntityManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.teamscore.java23.entities.CurrencyPair;
import ru.teamscore.java23.entities.ExchangeRate;
import ru.teamscore.java23.entities.PriceStatistics;
import ru.teamscore.java23.enums.Period;
import ru.teamscore.java23.statistics.StatisticsService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CurrencyPairManager {

    private final EntityManager entityManager;

//    CRUD
    public void saveCurrencyPair(@NonNull CurrencyPair currencyPair) {
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(currencyPair);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void updateCurrencyPair(CurrencyPair currencyPair) {
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(currencyPair);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Обработка ошибок при обновлении
        }
    }

    public void deleteCurrencyPairById(long id) {
        var currencyPair = entityManager.find(CurrencyPair.class, id);
        if (currencyPair != null) {
            deleteCurrencyPair(currencyPair);
        }
    }

    private void deleteCurrencyPair(CurrencyPair currencyPair) {
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(currencyPair);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Обработка ошибок при удалении
        }
    }

    public List searchCurrencyPairsByCurrencyName(String currencyName) {
        return entityManager
                .createNamedQuery("currencyByShortTitle")
                .setParameter("short_title", currencyName)
                .getResultList();
    }

    public Optional<CurrencyPair> getCurrencyPairByNames(String currMain, String currAdd) {
        return entityManager.createQuery("SELECT cp FROM CurrencyPair cp WHERE cp.baseCurrency.shortTitle = :currMain AND cp.quotedCurrency.shortTitle = :currAdd", CurrencyPair.class)
                .setParameter("currMain", currMain)
                .setParameter("currAdd", currAdd)
                .getResultList()
                .stream()
                .findFirst();
    }

    public List<CurrencyPair> getAllCurrencyPairs() {
        return entityManager.createQuery("SELECT cp FROM CurrencyPair cp", CurrencyPair.class)
                .getResultList();
    }

    public List<CurrencyPair> getCurrencyPairsByPrecision(int precision) {
        return entityManager.createNamedQuery("currencyPairsByPrecision", CurrencyPair.class)
                .setParameter("precision", precision)
                .getResultList();
    }



    public List<PriceStatistics> getCurrencyStatistics(String currency1, String currency2, int num, String period) {
        return StatisticsService.getStats(
                getCurrencyPairByNames(currency1, currency2).get(),
                num,
                Period.valueOf(period));
    }

    public List<PriceStatistics> getCurrencyStatistics(String currency1, String currency2, String startDate, String endDate, String period) {
        return StatisticsService.getStats(
                getCurrencyPairByNames(currency1, currency2).get(),
                startDate,
                endDate,
                Period.valueOf(period));
    }

}
