package com.github.jezorko.scrapper.domain.products


import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static TestData.EXAMPLE_PRODUCTS_LIST_DATA
import static TestData.EXAMPLE_PRODUCTS_LIST_PAGE

class ProductsBaseInformationExtractorSpecTest extends Specification {

    @Subject
    def extractor = new ProductsBaseInformationExtractor()

    @Unroll
    "should return an empty list if HTML #description"() {
        when:
          def result = extractor.apply ""

        then:
          result.isEmpty()

        where:
          html     | description
          null     | "is null"
          ""       | "does not contain any products"
          "<html<" | "is invalid"
    }

    def "should extract all example products from example HTML"() {
        when:
          def result = extractor.apply EXAMPLE_PRODUCTS_LIST_PAGE

        then:
          EXAMPLE_PRODUCTS_LIST_DATA.forEach { assert result.contains(it) }
    }

}
