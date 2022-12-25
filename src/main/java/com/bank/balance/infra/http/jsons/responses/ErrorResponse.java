package com.bank.balance.infra.http.jsons.responses;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    public final String uuid;
    private final String message;

    private final LocalDateTime date;

    public static ErrorResponse from(final String message) {
        return new ErrorResponse(UUID.randomUUID().toString(), message, LocalDateTime.now());
    }
}
