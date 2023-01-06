package com.bank.balance.infra.http.jsons.responses;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ErrorResponse {

    public final String uuid;
    private final String message;
    private Throwable throwable;
    private final LocalDateTime date;

    private ErrorResponse(final String uuid, final String message, final LocalDateTime date) {
        this.uuid = uuid;
        this.message = message;
        this.date = date;
    }

    private ErrorResponse(final String uuid, final String message, final Throwable throwable, final LocalDateTime date) {
        this.uuid = uuid;
        this.message = message;
        this.date = date;
        this.throwable = throwable;
    }

    public static ErrorResponse from(final String message) {
        return new ErrorResponse(UUID.randomUUID().toString(), message, LocalDateTime.now());
    }

    public static ErrorResponse from(final String message, final Throwable throwable) {
        return new ErrorResponse(UUID.randomUUID().toString(), message, throwable, LocalDateTime.now());
    }
}
