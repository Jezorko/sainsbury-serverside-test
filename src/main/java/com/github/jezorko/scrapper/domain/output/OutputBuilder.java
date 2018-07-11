package com.github.jezorko.scrapper.domain.output;

import com.github.jezorko.scrapper.domain.price.PriceAndTaxInPounds;
import com.github.jezorko.scrapper.domain.products.ProductDetails;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

class OutputBuilder {

    OutputData buildFrom(Collection<ProductDetails> productDetails, PriceAndTaxInPounds priceAndTaxInPounds) {
        return OutputData.builder()
                         .results(productDetails.stream()
                                                .map(OutputData.ProductDetailsData::new)
                                                .collect(toList()))
                         .total(new OutputData.PriceAndTaxInPoundsData(priceAndTaxInPounds))
                         .build();
    }

}
