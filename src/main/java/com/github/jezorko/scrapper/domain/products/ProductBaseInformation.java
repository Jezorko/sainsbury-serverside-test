package com.github.jezorko.scrapper.domain.products;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

/**
 * Represents product data that can be
 * found on the products list page.
 */
@Getter
@Builder
@EqualsAndHashCode
@RequiredArgsConstructor(access = PRIVATE)
public class ProductBaseInformation {
    private final String name;
    private final String detailsUrl;
    private final BigDecimal pricePerUnitInPounds;

    @Override
    public String toString() {
        return name + " (price=£" + pricePerUnitInPounds + "/unit, url=" + detailsUrl + ')';
    }
}
