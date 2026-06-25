package com.Remma.telegraph.messages;

import java.time.LocalDateTime;

public record MessageResponse(
        String text,
        String senderName,
        LocalDateTime sentAt
) {
}
