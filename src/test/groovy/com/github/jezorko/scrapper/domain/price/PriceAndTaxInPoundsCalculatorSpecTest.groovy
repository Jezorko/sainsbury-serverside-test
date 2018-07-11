package com.github.jezorko.scrapper.domain.price

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static java.math.BigDecimal.ZERO

class PriceAndTaxInPoundsCalculatorSpecTest extends Specification {

    @Subject
    def calculator = new PriceAndTaxCalculator()

    @Unroll
    "should calculate price and tax of #pricedItems as #expectedPriceAndTax"() {
        when:
          def actualPriceAndTax = calculator.calculateTotalOf pricedItems

        then:
          actualPriceAndTax == expectedPriceAndTax

        where:
          pricedItems                || expectedPriceAndTax
          []                         || priceAndTax(ZERO, ZERO)
          [null]                     || priceAndTax(ZERO, ZERO)
          [item(null)]               || priceAndTax(ZERO, ZERO)
          [item(1.00G)]              || priceAndTax(1.00G, 0.20G)
          [item(1.00G), item(2.00G)] || priceAndTax(3.00G, 0.60G)
          [item(0.01G), item(0.02G)] || priceAndTax(0.03G, 0.00G)
          [item(0.10G), item(0.20G)] || priceAndTax(0.30G, 0.06G)
          [item(0.11G), item(0.20G)] || priceAndTax(0.31G, 0.06G)
    }

    def "should throw if collection of items to calculate price of is null"() {
        when:
          calculator.calculateTotalOf null

        then:
          thrown NullPointerException
    }

    def priceAndTax(BigDecimal price, BigDecimal tax) {
        return PriceAndTaxInPounds.builder().price(price).tax(tax).build()
    }

    def item(BigDecimal price) {
        new PricedPerUnitInPounds() {
            @Override
            BigDecimal getPricePerUnitInPounds() {
                return price
            }

            String toString() {
                return "£${price}"
            }
        }
    }

}
