package com.yasiru.Sentinel.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.List;

public record ApiError(
        int status,
        String error,
        String message,
        String path,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
        Instant timestamp,

        List<String> validationErrors
) {

    public static ApiError of(int status, String error, String message, String path) {
        return new ApiError(status, error, message, path, Instant.now(), null);
    }

    public static ApiError of(int status, String error, String message, String path,
                              List<String> validationErrors
    ) {
        return new ApiError(status, error, message, path, Instant.now(), validationErrors);
    }

}
