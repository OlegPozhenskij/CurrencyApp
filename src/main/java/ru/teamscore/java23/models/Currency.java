package ru.teamscore.java23.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "currency", schema = "currencies")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id = 0L;

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

    public Currency(Long id, String shortTitle, String fullTitle) {
        this(shortTitle, fullTitle);
        this.id = id;
    }
}
