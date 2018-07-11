package com.github.jezorko.scrapper.domain.output;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;

class OutputSerializer {

    private final static TypeAdapter<OutputData> GSON_ADAPTER = new GsonBuilder().setPrettyPrinting()
                                                                                 .create()
                                                                                 .getAdapter(OutputData.class);

    String serialize(OutputData outputData) {
        return GSON_ADAPTER.toJson(outputData);
    }

}
