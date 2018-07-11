package com.github.jezorko.scrapper.domain.products

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static TestData.EXAMPLE_PRODUCTS_LIST_PAGE
import static com.github.jezorko.scrapper.domain.products.TestData.EXAMPLE_PRODUCTS_LIST_DATA

class ProductsBaseInformationExtractorSpecTest extends Specification {

    @Subject
    def extractor = new ProductsBaseInformationExtractor()

    @Unroll
    "should return an empty list if HTML is #description"() {
        when:
          def result = extractor.apply html

        then:
          result.isEmpty()

        where:
          html     | description
          null     | "null"
          ""       | "empty"
          "<html<" | "invalid"
    }

    @Unroll
    "should return #expectedProduct for HTML with name='#name', url='#url' and price='#price'"() {
        given:
          def productHtml = productBaseInformationHtml(name, url, price)

        when:
          def actualProduct = extractor.apply productHtml

        then:
          actualProduct.size() == 1 as int
          actualProduct.get(0) == expectedProduct

        where:
          name   | url    | price     || expectedProduct
          'test' | 'test' | '1.0'     || ProductBaseInformation.builder().name('test').detailsUrl('test').pricePerUnitInPounds(1.0G).build()
          ''     | 'test' | '1.0'     || ProductBaseInformation.builder().name('').detailsUrl('test').pricePerUnitInPounds(1.0G).build()
          'test' | ''     | '1.0'     || ProductBaseInformation.builder().name('test').detailsUrl('').pricePerUnitInPounds(1.0G).build()
          'test' | 'test' | 'invalid' || ProductBaseInformation.builder().name('test').detailsUrl('test').pricePerUnitInPounds(null).build()
    }

    def "should extract all example products from example HTML"() {
        when:
          def result = extractor.apply EXAMPLE_PRODUCTS_LIST_PAGE

        then:
          EXAMPLE_PRODUCTS_LIST_DATA.forEach { assert result.contains(it) }
    }

    def "should extract each example product from the HTML representation"() {
        given:
          def productAsHtml = productBaseInformationHtml product

        when:
          def result = extractor.apply productAsHtml

        then:
          result.size() == 1 as int
          result.get(0) == product

        where:
          product << EXAMPLE_PRODUCTS_LIST_DATA
    }

    static productBaseInformationHtml(ProductBaseInformation product) {
        productBaseInformationHtml(product.name, product.detailsUrl, product.pricePerUnitInPounds)
    }

    static productBaseInformationHtml(name, url, price) {
        """
            <div class="product">
                <div class="productNameAndPromotions">
                    <h3>
                        <a href="${url}">${name}</a>
                    </h3>
                </div>
                <div class="pricePerUnit">${price}</div>
            </div>
        """
    }

}
