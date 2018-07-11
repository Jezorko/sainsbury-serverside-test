package com.github.jezorko.scrapper.domain.products;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PACKAGE;

/**
 * Extracts products information from the products list page.
 */
@RequiredArgsConstructor(access = PACKAGE)
class ProductsBaseInformationExtractor implements Function<String, List<ProductBaseInformation>> {

    private final static String PRODUCT_ELEMENT_CLASS_SELECTOR = "product";
    private final static String PRODUCT_NAME_AND_DETAILS_URL_CSS_SELECTOR = ".productNameAndPromotions > h3 > a";
    private final static String PRICE_PER_UNIT_CSS_CLASS = "pricePerUnit";
    private final static String URL_ATTRIBUTE_NAME = "href";

    @Override
    public List<ProductBaseInformation> apply(String html) {
        return Jsoup.parse(html)
                    .getElementsByClass(PRODUCT_ELEMENT_CLASS_SELECTOR)
                    .stream()
                    .map(this::htmlProductToDomainObject)
                    .collect(toList());
    }

    private ProductBaseInformation htmlProductToDomainObject(Element element) {
        Element nameAndDetailsUrlElement = element.selectFirst(PRODUCT_NAME_AND_DETAILS_URL_CSS_SELECTOR);
        BigDecimal pricePerUnit = scapPriceValueFrom(element.getElementsByClass(PRICE_PER_UNIT_CSS_CLASS)
                                                            .first());
        return ProductBaseInformation.builder()
                                     .name(nameAndDetailsUrlElement.text())
                                     .detailsUrl(nameAndDetailsUrlElement.attr(URL_ATTRIBUTE_NAME))
                                     .pricePerUnitInPounds(pricePerUnit)
                                     .build();
    }

    private BigDecimal scapPriceValueFrom(Element pricePerUnitElement) {
        return new BigDecimal(pricePerUnitElement.text()
                                                 .replaceAll("[^\\d.]+", ""));
    }
}
