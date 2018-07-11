package com.github.jezorko.scrapper.domain.output;

import com.google.gson.GsonBuilder;

import java.util.function.Function;

class OutputSerializer {

    private final static Function<OutputData, String> GSON = new GsonBuilder().setPrettyPrinting()
                                                                              .disableHtmlEscaping()
                                                                              .create()::toJson;

    String serialize(OutputData outputData) {
        return GSON.apply(outputData);
    }

}
