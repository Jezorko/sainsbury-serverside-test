package com.github.jezorko.scrapper;

import com.github.jezorko.scrapper.domain.http.HttpStringGetter;
import com.github.jezorko.scrapper.domain.price.PriceAndTaxCalculator;
import com.github.jezorko.scrapper.domain.price.PriceAndTaxInPounds;
import com.github.jezorko.scrapper.domain.price.PricedPerUnitInPounds;
import com.github.jezorko.scrapper.domain.products.ProductBaseInformation;
import com.github.jezorko.scrapper.domain.products.ProductDetails;
import com.mashape.unirest.http.HttpResponse;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.List;
import java.util.stream.Stream;

import static com.github.jezorko.scrapper.domain.output.OutputFacade.toProgramOutput;
import static com.github.jezorko.scrapper.domain.products.ProductsFacade.extractProductDetailsFrom;
import static com.github.jezorko.scrapper.domain.products.ProductsFacade.extractProductsBaseInformation;
import static java.util.stream.Collectors.toList;

public class SainsburyGroceriesScrapperApplication {

    private final static String URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/" +
                                      "webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";

    public static void main(String... args) {
        final HttpStringGetter httpStringGetter = new HttpStringGetter();
        final List<ProductDetails> productsDetails = Stream.of(URL)
                                                           .map(httpStringGetter)
                                                           .map(HttpResponse::getBody)
                                                           .flatMap(html -> extractProductsBaseInformation(html).stream())
                                                           .map(product -> extractProductDetailsFrom(product, getProductDetailsHtml(httpStringGetter, product)))
                                                           .collect(toList());

        final PriceAndTaxInPounds totalPriceAndTax = new PriceAndTaxCalculator().calculateTotalOf(productsDetails.stream()
                                                                                                                 .map(product -> (PricedPerUnitInPounds) product::getPricePerUnitInPounds)
                                                                                                                 .collect(toList()));

        final String output = toProgramOutput(productsDetails, totalPriceAndTax);
        System.out.println(output);
    }

    @SneakyThrows
    private static String getProductDetailsHtml(HttpStringGetter httpStringGetter, ProductBaseInformation product) {
        String url = new URL(new URL(URL), product.getDetailsUrl()).toString();
        return httpStringGetter.apply(url)
                               .getBody();
    }

}
