package com.bank.balance.infra.http.converters;

import com.bank.balance.domain.BalanceEntry;
import com.bank.balance.domain.UserBalanceEntry;
import com.bank.balance.infra.exceptions.ConvertFileException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class UserBalanceEntryConverter {

    private final ObjectMapper objectMapper;

    public UserBalanceEntryConverter(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Converter<MultipartFile, UserBalanceEntry> toDomain(final String customerId) {
        return (file) -> {
            final var balanceEntries = getBalanceEntries(file);
            return UserBalanceEntry.of(customerId, balanceEntries);
        };
    }

    private List<BalanceEntry> getBalanceEntries(final MultipartFile file) {
        try {
            final var json = new String(file.getBytes());
            return objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (final IOException e) {
            log.error("Error {} ", e.getMessage());
            throw new ConvertFileException(e.getMessage(), e.getCause());
        }
    }
}
