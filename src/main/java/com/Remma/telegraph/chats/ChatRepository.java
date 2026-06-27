package com.Remma.telegraph.chats;

import com.Remma.telegraph.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    Optional<ChatEntity> findByUser1AndUser2(UserEntity user1, UserEntity user2);

    @Query("""
            SELECT c
            FROM ChatEntity c
            WHERE c.user1.id = :userId
            OR c.user2.id = :userId
            """)
    List<ChatEntity> findAllByUser(
            @Param("userId") Long userId
    );
}
