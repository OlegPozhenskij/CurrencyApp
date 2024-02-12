package ru.teamscore.java23;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.teamscore.java23.entities.CurrencyPair;
import ru.teamscore.java23.entities.PriceStatistics;
import ru.teamscore.java23.enums.Period;
import ru.teamscore.java23.services.StatisticsService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CurrencyPairManager {

    private final EntityManager entityManager;

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
            throw e; // ADD ex
        }
    }

    public void deleteCurrencyPairById(@NonNull CurrencyPair currencyPair) {
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(entityManager.contains(currencyPair)
                    ? currencyPair
                    : entityManager.merge(currencyPair));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // DEL ex
        }
    }


    public List searchCurrencyPairsByCurrencyName(String currencyName) {
        return entityManager
                .createNamedQuery("currencyByShortTitle")
                .setParameter("short_title", currencyName)
                .getResultList();
    }
//
//    public Optional<CurrencyPair> getCurrencyPairByNames(String currMain, String currAdd) {
//        return entityManager.createQuery("SELECT cp FROM CurrencyPair cp WHERE cp.baseCurrency.shortTitle = :currMain AND cp.quotedCurrency.shortTitle = :currAdd", CurrencyPair.class)
//                .setParameter("currMain", currMain)
//                .setParameter("currAdd", currAdd)
//                .getResultList()
//                .stream()
//                .findFirst();
//    }

//    public List<PriceStatistics> getCurrencyStatistics(String currency1, String currency2, int num, String period) {
//        return StatisticsService.getStats(
//                getCurrencyPairByNames(currency1, currency2),
//                num,
//                Period.valueOf(period));
//    }
//
//    public List<PriceStatistics> getCurrencyStatistics(String currency1, String currency2, String startDate, String endDate, String period) {
//        return StatisticsService.getStats(
//                getCurrencyPairByNames(currency1, currency2),
//                startDate,
//                endDate,
//                Period.valueOf(period));
//    }
//

}
