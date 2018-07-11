package com.github.jezorko.scrapper.domain.output

import com.github.jezorko.scrapper.domain.price.PriceAndTaxInPounds
import com.github.jezorko.scrapper.domain.products.ProductDetails
import spock.lang.Specification
import spock.lang.Subject

class OutputBuilderSpecTest extends Specification {

    @Subject
    def builder = new OutputBuilder()

    def "should build an output data object from given product details, price and tax"() {
        given:
          def name = GroovyMock String
          def description = GroovyMock String
          def calories = GroovyMock BigInteger
          def price = GroovyMock BigDecimal
          def details = ProductDetails.builder()
                                      .name(name)
                                      .description(description)
                                      .caloriesPer100Grams(calories)
                                      .pricePerUnitInPounds(price)
                                      .build()

          def priceTotal = GroovyMock BigDecimal
          def tax = GroovyMock BigDecimal
          def priceAndTax = PriceAndTaxInPounds.builder()
                                               .price(priceTotal)
                                               .tax(tax)
                                               .build()

        when:
          def result = builder.buildFrom([details], priceAndTax)

        then: "results contain a single element"
          result["results"].size() == 1

        and: "that element is the same as product"
          result["results"][0].name == name
          result["results"][0].description == description
          result["results"][0].caloriesPer100Grams == calories
          result["results"][0].pricePerUnitInPounds == price

        and: "price and tax matches with given price and tax"
          result["total"].price == priceTotal
          result["total"].tax == tax

    }

}
