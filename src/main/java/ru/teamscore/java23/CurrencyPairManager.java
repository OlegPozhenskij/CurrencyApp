package ru.teamscore.java23;

import ru.teamscore.java23.entities.CurrencyPair;
import ru.teamscore.java23.entities.PriceStatistics;
import ru.teamscore.java23.enums.Period;
import ru.teamscore.java23.services.StatisticsService;

import java.util.List;

public class CurrencyPairManager {
    private final List<CurrencyPair> currencyPairRepository;

    public CurrencyPairManager(List<CurrencyPair> currencyPairRepository) {
        this.currencyPairRepository = currencyPairRepository;
    }

    public List<CurrencyPair> searchCurrencyPairsByCurrencyName(String currencyName) {
        return currencyPairRepository.stream().filter(currencyPair ->
                currencyPair.getBaseCurrency().getShortTitle().equals(currencyName)
        ).toList();
    }

    public List<PriceStatistics> getCurrencyStatistics(String currency1, String currency2, int num, String period) {
        return StatisticsService.getStats(
                getCurrencyPairByNames(currency1, currency2),
                num,
                Period.valueOf(period));
    }

    public List<PriceStatistics> getCurrencyStatistics(String currency1, String currency2, String startDate, String endDate, String period) {
        return StatisticsService.getStats(
                getCurrencyPairByNames(currency1, currency2),
                startDate,
                endDate,
                Period.valueOf(period));
    }

    public void saveCurrencyPair(CurrencyPair currencyPair) {
        currencyPairRepository.add(currencyPair);
    }

    public void deleteCurrencyPairById(CurrencyPair currencyPair) {
        currencyPairRepository.remove(currencyPair);
    }

    public CurrencyPair getCurrencyPairByNames(String currMain, String currAdd) {
        return currencyPairRepository
                .stream()
                .filter(currencyPair -> currencyPair.getBaseCurrency().getShortTitle().equals(currMain))
                .filter(currencyPair -> currencyPair.getQuotedCurrency().getShortTitle().equals(currAdd))
                .findFirst().orElse(null);
    }
}
