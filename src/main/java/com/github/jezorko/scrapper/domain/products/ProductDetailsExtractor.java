package com.github.jezorko.scrapper.domain.products;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.BigInteger;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

import static java.util.Optional.ofNullable;

/**
 * Extracts product's details from the details page.
 */
class ProductDetailsExtractor implements BiFunction<ProductBaseInformation, String, ProductDetails> {

    private final static String INFORMATION_SECTION_ID = "information";
    private final static String CALORIES_CSS_SELECTOR = ".nutritionTable > tbody > tr:nth-child(2) > td:nth-child(1)";
    private final static String DESCRIPTION_TITLE = "Description";

    private final static String CALORIES_DISALLOWED_CHARACTERS_REGEX = "\\D+";

    @Override
    public ProductDetails apply(ProductBaseInformation productBaseInformation, String productDetailsHtml) {
        final Optional<Element> productInformationDocument = ofNullable(productDetailsHtml).map(Jsoup::parse)
                                                                                           .map(document -> document.getElementById(INFORMATION_SECTION_ID));
        return ProductDetails.builder()
                             .name(productBaseInformation.getName())
                             .description(productInformationDocument.flatMap(this::extractDescriptionFromProductInformation)
                                                                    .orElse(null))
                             .pricePerUnitInPounds(productBaseInformation.getPricePerUnitInPounds())
                             .caloriesPer100Grams(productInformationDocument.flatMap(this::extractCaloriesFromProductInformation)
                                                                            .orElse(null))
                             .build();
    }

    private Optional<BigInteger> extractCaloriesFromProductInformation(Element productInformation) {
        return Optional.of(productInformation)
                       .map(html -> html.select(CALORIES_CSS_SELECTOR))
                       .map(Elements::first)
                       .map(Element::text)
                       .map(text -> text.replaceAll(CALORIES_DISALLOWED_CHARACTERS_REGEX, ""))
                       .filter(text -> !text.isEmpty())
                       .map(BigInteger::new);
    }

    private Optional<String> extractDescriptionFromProductInformation(Element productInformation) {
        return productInformation.getElementsMatchingText(DESCRIPTION_TITLE)
                                 .parents()
                                 .last()
                                 .children()
                                 .stream()
                                 .map(Element::text)
                                 .filter(Objects::nonNull)
                                 .filter(text -> !DESCRIPTION_TITLE.equals(text))
                                 .findFirst();
    }

}
