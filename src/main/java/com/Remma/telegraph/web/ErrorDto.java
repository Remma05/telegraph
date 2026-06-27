package com.Remma.telegraph.web;

import java.time.LocalDateTime;

public record ErrorDto(
        String message,
        String detailMessage,
        LocalDateTime time
) {
}
