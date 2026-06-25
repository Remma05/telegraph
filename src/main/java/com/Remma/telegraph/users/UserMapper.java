package com.Remma.telegraph.users;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity toEntity(
            Long id,
            UserRequest user
    ) {
        return new UserEntity(
                id,
                user.username(),
                user.password()
        );
    }

    public UserResponse toDomain(
            UserEntity entity
    ) {
        return new UserResponse(
                entity.getId(),
                entity.getUsername()
        );
    }
}
