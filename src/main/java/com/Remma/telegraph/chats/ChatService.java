package com.Remma.telegraph.chats;

import com.Remma.telegraph.users.UserEntity;
import com.Remma.telegraph.users.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    private final ChatRepository repository;
    private final UserService userService;

    public ChatService(
            ChatRepository repository,
            UserService userService
    ) {
        this.repository = repository;
        this.userService = userService;
    }

    public ChatEntity getOrCreateChat(UserEntity first, UserEntity second) {
        UserEntity user1 = first.getId() < second.getId() ? first : second;
        UserEntity user2 = first.getId() < second.getId() ? second : first;

        return repository.findByUser1AndUser2(user1, user2)
                .orElseGet(() -> {
                    ChatEntity chat = new ChatEntity();
                    chat.setUser1(user1);
                    chat.setUser2(user2);
                    return repository.save(chat);
                });
    }

    public List<ChatResponse> getAllChats() {
        UserEntity user = userService.getCurrentUserEntity();
        return repository.findAllByUser(user.getId())
                .stream()
                .map(chat -> {
                    UserEntity companion = chat.getUser1().getId().equals(user.getId())
                            ? chat.getUser2()
                            : chat.getUser1();
                    return new ChatResponse(companion.getId(), companion.getUsername());
                })
                .toList();
    }
}
