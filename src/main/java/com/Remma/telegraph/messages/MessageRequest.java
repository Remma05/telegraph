package com.Remma.telegraph.messages;

public record MessageRequest(
        Long receiverId,
        String text
) {
}
