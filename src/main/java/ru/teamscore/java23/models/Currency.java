package ru.teamscore.java23.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "currency", schema = "currencies")
@NamedQuery(name = "currenciesCount", query = "select count(*) from Currency")
@NamedQuery(name = "currencyByShortTitle", query = "from Currency c where c.shortTitle = :shortTitle")
public class Currency {

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Setter
    @Column(name = "short_title", nullable = false, unique = true)
    private String shortTitle;

    @Setter
    @Column(name = "full_title", nullable = false)
    private String fullTitle;

    @OneToMany(mappedBy = "baseCurrency", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CurrencyPair> baseCurrencyPairs = new HashSet<>();

    @OneToMany(mappedBy = "quotedCurrency", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CurrencyPair> quotedCurrencyPairs = new HashSet<>();

    public Currency(String shortTitle, String fullTitle) {
        this.shortTitle = shortTitle;
        this.fullTitle = fullTitle;
    }
}
