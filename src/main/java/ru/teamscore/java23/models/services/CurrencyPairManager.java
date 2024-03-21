package ru.teamscore.java23.models.services;

import jakarta.persistence.EntityManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.teamscore.java23.models.enums.Period;
import ru.teamscore.java23.models.exceptions.CurrencyNotFoundException;
import ru.teamscore.java23.models.CurrencyPair;
import ru.teamscore.java23.models.PriceStatistics;
import ru.teamscore.java23.models.statistics.StatisticsService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



@Component
@RequiredArgsConstructor
public class CurrencyPairManager {

    @Autowired
    private final EntityManager entityManager;

    @Autowired
    private final CurrencyManager currencyManager;

    public void saveOrUpdateCurrencyPair(@NonNull CurrencyPair currencyPair) {
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if ((currencyPair.getId() == 0)) {
                if(!entityManager.contains(currencyPair.getBaseCurrency())
                        || !entityManager.contains(currencyPair.getQuotedCurrency())) {

                    entityManager.merge(currencyPair.getBaseCurrency());
                    entityManager.merge(currencyPair.getQuotedCurrency());
                }
                entityManager.persist(currencyPair);
            } else {
                entityManager.merge(currencyPair);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
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
            throw e;
        }
    }

    public CurrencyPair getCurrencyPairById(long id) {
        return entityManager.find(CurrencyPair.class, id);
    }

    public CurrencyPair searchCurrencyPairsByCurrencyName(String baseCurrency, String quotedCurrency) {
        var bc = currencyManager.getCurrencyByShortTitle(baseCurrency);
        var qc = currencyManager.getCurrencyByShortTitle(quotedCurrency);
        if (bc != null && qc != null) {
            return  entityManager.createNamedQuery("currencyPairByShortTitles", CurrencyPair.class)
                    .setParameter("baseCurrency", bc)
                    .setParameter("quotedCurrency", qc)
                    .getSingleResult();
        }
        throw new CurrencyNotFoundException("Проблема поиска CurrencyPair, по shortName", baseCurrency, quotedCurrency);
    }

    public Optional<CurrencyPair> getCurrencyPairByNames(String currMain, String currAdd) {
        return entityManager.createQuery("SELECT cp FROM CurrencyPair cp WHERE cp.baseCurrency = :currMain AND cp.quotedCurrency = :currAdd", CurrencyPair.class)
                .setParameter("currMain", currencyManager.getCurrencyByShortTitle(currMain))
                .setParameter("currAdd", currencyManager.getCurrencyByShortTitle(currAdd))
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
                entityManager,
                getCurrencyPairByNames(currency1, currency2).get(),
                num,
                Period.valueOf(period));
    }

    public List<PriceStatistics> getCurrencyStatistics(String currency1, String currency2, LocalDateTime startDate, LocalDateTime endDate, String period) {
        return StatisticsService.getStats(
                entityManager,
                getCurrencyPairByNames(currency1, currency2).get(),
                startDate,
                endDate,
                Period.valueOf(period));
    }

}
