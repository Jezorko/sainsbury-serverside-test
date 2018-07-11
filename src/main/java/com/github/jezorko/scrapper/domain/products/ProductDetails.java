package com.github.jezorko.scrapper.domain.products;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

/**
 * Represents product data that can be
 * found on the product details page.
 */
@Getter
@Builder
@EqualsAndHashCode
@RequiredArgsConstructor(access = PRIVATE)
public class ProductDetails {
    private final String name;
    private final String description;
    private final BigInteger caloriesPer100Grams;
    private final BigDecimal pricePerUnitInPounds;

    @Override
    public String toString() {
        return name + Optional.ofNullable(description)
                              .map(" - "::concat)
                              .orElse("") +
               " (price=£" + pricePerUnitInPounds + "/unit, " +
               "calories=" + Optional.ofNullable(caloriesPer100Grams)
                                     .map(calories -> calories + "kcal")
                                     .orElse("?") + ")";
    }
}
