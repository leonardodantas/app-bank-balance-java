package com.bank.balance.infra.http.converters;

import com.bank.balance.domain.BalanceEntry;
import com.bank.balance.domain.UserBalanceEntries;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class UserBalanceEntryConverter {

    private final JSONParser parser;
    private final ObjectMapper objectMapper;

    public UserBalanceEntryConverter(final JSONParser parser, final ObjectMapper objectMapper) {
        this.parser = parser;
        this.objectMapper = objectMapper;
    }

    public Converter<MultipartFile, UserBalanceEntries> toDomain(final String customerId) {
        return (file) -> {
            final var balanceEntries = getBalanceEntries(file);
            return UserBalanceEntries.of(customerId, balanceEntries);
        };
    }

    private List<BalanceEntry> getBalanceEntries(final MultipartFile file) {
        try {
            final var parse = (JSONObject) parser.parse(new FileReader(
                    file.getContentType()));
            return objectMapper.readValue(parse.toJSONString(), new TypeReference<>() {
            });
        } catch (final IOException | ParseException e) {
            log.error("Error {} ");
            throw new RuntimeException(e);
        }
    }
}
