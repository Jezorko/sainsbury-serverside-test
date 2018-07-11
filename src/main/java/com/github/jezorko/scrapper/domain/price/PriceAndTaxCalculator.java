package com.github.jezorko.scrapper.domain.price;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Objects;

import static java.math.RoundingMode.FLOOR;

public class PriceAndTaxCalculator {

    private final static BigDecimal TAX_PROPORTION = new BigDecimal("0.2");

    /**
     * As per section 17.5 of <a href="https://www.gov.uk/guidance/vat-guide-notice-700#section17">HMRC VAT Guide</a>.
     */
    private final static RoundingMode TAX_ROUNDING_MODE = FLOOR;

    public PriceAndTaxInPounds calculateTotalOf(Collection<PricedPerUnitInPounds> pricedItems) {
        return pricedItems.stream()
                          .filter(Objects::nonNull)
                          .map(PricedPerUnitInPounds::getPricePerUnitInPounds)
                          .filter(Objects::nonNull)
                          .reduce(BigDecimal::add)
                          .map(totalPrice -> PriceAndTaxInPounds.builder()
                                                                .price(totalPrice)
                                                                .tax(calculateVatFrom(totalPrice))
                                                                .build())
                          .orElse(PriceAndTaxInPounds.EMPTY);
    }

    private BigDecimal calculateVatFrom(BigDecimal totalPrice) {
        return totalPrice.multiply(TAX_PROPORTION)
                         .setScale(2, TAX_ROUNDING_MODE);
    }

}
