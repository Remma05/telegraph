package com.Remma.telegraph.users;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
