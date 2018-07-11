package com.github.jezorko.scrapper.domain.output;

import com.github.jezorko.scrapper.domain.price.PriceAndTaxInPounds;
import com.github.jezorko.scrapper.domain.products.ProductDetails;

import java.util.Collection;

public class OutputFacade {

    private final static OutputBuilder OUTPUT_BUILDER = new OutputBuilder();
    private final static OutputSerializer OUTPUT_SERIALIZER = new OutputSerializer();

    public static String toProgramOutput(Collection<ProductDetails> productsDetails, PriceAndTaxInPounds priceAndTaxInPounds) {
        final OutputData data = OUTPUT_BUILDER.buildFrom(productsDetails, priceAndTaxInPounds);
        return OUTPUT_SERIALIZER.serialize(data);
    }

}
