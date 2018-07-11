package com.github.jezorko.scrapper.domain.products;

import java.util.List;

/**
 * Public entry point to the products domain.
 * Allows products data extraction from HTML sources.
 */
public final class ProductsFacade {

    public static List<ProductBaseInformation> extractProductsBaseInformation(String productsListPageHtml) {
        return new ProductsBaseInformationExtractor().apply(productsListPageHtml);
    }

    public static ProductDetails extractProductDetailsFrom(ProductBaseInformation information, String detailsPageHtml) {
        return new ProductDetailsExtractor().apply(information, detailsPageHtml);
    }

}
