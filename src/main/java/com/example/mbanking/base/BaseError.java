package com.example.mbanking.base;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record BaseError<T>(boolean status,
                        Integer code,
                        String message,
                        LocalDateTime timestamp,
                        T errors) {
}
