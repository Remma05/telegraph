package com.Remma.telegraph.messages;

import jakarta.validation.constraints.NotBlank;

public record MessageRequest(
        @NotBlank
        String text
) {
}
