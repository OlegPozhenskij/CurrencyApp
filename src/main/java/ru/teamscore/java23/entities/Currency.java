package ru.teamscore.java23.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "currency", schema = "currencies")
@NamedQuery(name = "currenciesCount", query = "select count(*) from Currency")
@NamedQuery(name = "currencyByShortTitle", query = "from Currency c where c.shortTitle = :shortTitle")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency currency)) return false;
        return Objects.equals(id, currency.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
