package com.github.jezorko.scrapper.domain.output;

import com.github.jezorko.scrapper.domain.price.PriceAndTaxInPounds;
import com.github.jezorko.scrapper.domain.products.ProductDetails;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

import static lombok.AccessLevel.PRIVATE;

@Builder
@RequiredArgsConstructor(access = PRIVATE)
class OutputData {
    private final Collection<ProductDetailsData> results;
    private final PriceAndTaxInPoundsData total;

    static class ProductDetailsData {
        @SerializedName("title")
        private final String name;
        private final String description;
        @SerializedName("kcal_per_100g")
        private final BigInteger caloriesPer100Grams;
        @SerializedName("unit_price")
        private final BigDecimal pricePerUnitInPounds;

        ProductDetailsData(ProductDetails productDetails) {
            name = productDetails.getName();
            description = productDetails.getDescription();
            caloriesPer100Grams = productDetails.getCaloriesPer100Grams();
            pricePerUnitInPounds = productDetails.getPricePerUnitInPounds();
        }
    }

    static class PriceAndTaxInPoundsData {
        @SerializedName("gross")
        private final BigDecimal price;
        @SerializedName("vat")
        private final BigDecimal tax;

        PriceAndTaxInPoundsData(PriceAndTaxInPounds priceAndTaxInPounds) {
            price = priceAndTaxInPounds.getPrice();
            tax = priceAndTaxInPounds.getTax();
        }
    }
}
