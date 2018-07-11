package com.github.jezorko.scrapper.domain.http;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import lombok.SneakyThrows;

import java.util.function.Function;

/**
 * Makes a HTTP GET request with {@link Unirest#get(String)}.
 */
public class HttpStringGetter implements Function<String, HttpResponse<String>> {

    @Override
    @SneakyThrows
    public HttpResponse<String> apply(String url) {
        return Unirest.get(url)
                      .asString();
    }

}
