package com.github.jezorko.scrapper.domain.products

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static com.github.jezorko.scrapper.domain.products.TestData.EXAMPLE_PRODUCT_DETAILS_PAGES_AND_DATA

class ProductDetailsExtractorSpecTest extends Specification {

    @Subject
    def extractor = new ProductDetailsExtractor()

    @Unroll
    "should only copy base information contents if HTML is #description"() {
        given:
          def name = GroovyMock String
          def price = Mock BigDecimal
          def baseInformation = ProductBaseInformation.builder()
                                                      .name(name)
                                                      .detailsUrl(_ as String)
                                                      .pricePerUnitInPounds(price)
                                                      .build()

        when:
          def result = extractor.apply(baseInformation, html)

        then:
          result.name == name
          result.pricePerUnitInPounds == price

        where:
          html     | description
          null     | "null"
          ""       | "empty"
          "<html<" | "invalid"
    }

    @Unroll
    "should extract calories=#expectedCalories and description=#expectedDescription from given HTML"() {
        when:
          def result = extractor.apply(ProductBaseInformation.builder().build(), html)

        then:
          result.description == expectedDescription
          result.caloriesPer100Grams == expectedCalories

        where:
          html                                || expectedDescription | expectedCalories
          productDetailsHtml("test", "1")     || "test"              | 1G
          productDetailsHtml("test", "1kcal") || "test"              | 1G
          productDetailsHtml("test", "none")  || "test"              | null
          productDetailsHtml("", "bla1bla")   || ""                  | 1G
    }

    def "should extract product details from each example details page"() {
        when:
          def result = extractor.apply(ProductBaseInformation.builder().build(), productDetailsHtml)

        then:
          result.description == expectedProductDetails.description
          result.caloriesPer100Grams == expectedProductDetails.caloriesPer100Grams

        where:
          pagesAndData << EXAMPLE_PRODUCT_DETAILS_PAGES_AND_DATA
          productDetailsHtml = pagesAndData.html
          expectedProductDetails = pagesAndData.details
    }

    static productDetailsHtml(description, calories) {
        """
            <div id="information">
                <table class="nutritionTable">
                    <thead>not important</thead>
                    <tbody>
                        <tr>ignore</tr>
                        <tr>
                            <td>
                                <div>${calories}</div>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <doesNotMatter>
                    <randomTagName>
                        <div>Description</div>
                        <div>${description}</div>
                    </randomTagName>
                </doesNotMatter>
            </div>
        """
    }

}
