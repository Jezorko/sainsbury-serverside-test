package com.github.jezorko.scrapper.domain.price;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static lombok.AccessLevel.PRIVATE;

/**
 * Represents a price and its' tax.
 */
@Getter
@Builder
@EqualsAndHashCode
@RequiredArgsConstructor(access = PRIVATE)
public class PriceAndTaxInPounds {

    final static PriceAndTaxInPounds EMPTY = new PriceAndTaxInPounds(ZERO, ZERO);

    private final BigDecimal price;
    private final BigDecimal tax;

    @Override
    public String toString() {
        return "£" + price + " (tax=£" + tax + ")";
    }
}
