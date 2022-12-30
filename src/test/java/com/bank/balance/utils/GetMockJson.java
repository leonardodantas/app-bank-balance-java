package com.bank.balance.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetMockJson {

    private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    private final ObjectMapper objectMapper = getObjectMapper();

    public ObjectMapper getObjectMapper() {
        final var javaTimeModule = new JavaTimeModule();

        final var localDateTimeDeserializer = new
                LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(dateTimeFormat));
        javaTimeModule.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);

        return Jackson2ObjectMapperBuilder.json()
                .modules(javaTimeModule)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
    }

    public <T> T execute(final String fileName, final Class<T> klass) {
        try {
            final var pathFile = String.format("src/test/resources/mocks/%s.json", fileName);
            final var jsonObject = (JsonObject) JsonParser.parseReader(new FileReader(pathFile));
            return objectMapper.readValue(jsonObject.toString(), klass);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T execute(final String fileName, TypeReference<T> klass) {
        try {
            final var pathFile = String.format("src/test/resources/mocks/%s.json", fileName);
            final var jsonArray = (JsonArray) JsonParser.parseReader(new FileReader(pathFile));
            return objectMapper.readValue(jsonArray.toString(), klass);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
