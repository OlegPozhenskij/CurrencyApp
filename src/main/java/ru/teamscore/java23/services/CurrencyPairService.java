package ru.teamscore.java23.services;

import jakarta.persistence.EntityManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.teamscore.java23.models.enums.Period;
import ru.teamscore.java23.models.exceptions.CurrencyNotFoundException;
import ru.teamscore.java23.models.CurrencyPair;
import ru.teamscore.java23.models.PriceStatistics;
import ru.teamscore.java23.models.statistics.StatisticsService;
import ru.teamscore.java23.repositories.CurrencyPairRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class CurrencyPairService {
    private final EntityManager entityManager;
    private final CurrencyService currencyService;
    private final CurrencyPairRepository currencyPairRepository;

    public void saveOrUpdateCurrencyPair(@NonNull CurrencyPair currencyPair) {
        currencyPairRepository.save(currencyPair);
    }

    public void deleteCurrencyPairById(long id) {
        currencyPairRepository.deleteById(id);
    }

    private void deleteCurrencyPair(CurrencyPair currencyPair) {
        currencyPairRepository.delete(currencyPair);
    }

    public Optional<CurrencyPair> getCurrencyPairById(long id) {
        return currencyPairRepository.findById(id);
    }

    public CurrencyPair searchCurrencyPairsByCurrencyName(String baseCurrency, String quotedCurrency) {
        var bc = currencyService.getCurrencyByShortTitle(baseCurrency);
        var qc = currencyService.getCurrencyByShortTitle(quotedCurrency);
        if (bc != null && qc != null) {
            return  currencyPairRepository.findCurrencyPairsByCurrencyName(bc, qc);
        }
        throw new CurrencyNotFoundException("Проблема поиска CurrencyPair, по shortName", baseCurrency, quotedCurrency);
    }

    public List<CurrencyPair> getAllCurrencyPairs() {
        return (List<CurrencyPair>) currencyPairRepository.findAll();
    }

    public List<PriceStatistics> getCurrencyStatistics(String currency1, String currency2, int num, String period) {
        return StatisticsService.getStats(
                entityManager,
                searchCurrencyPairsByCurrencyName(currency1, currency2),
                num,
                Period.valueOf(period));
    }

    public List<PriceStatistics> getCurrencyStatistics(String currency1, String currency2, LocalDateTime startDate, LocalDateTime endDate, String period) {
        return StatisticsService.getStats(
                entityManager,
                searchCurrencyPairsByCurrencyName(currency1, currency2),
                startDate,
                endDate,
                Period.valueOf(period));
    }
}
