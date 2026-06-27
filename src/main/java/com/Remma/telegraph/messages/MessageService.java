package com.Remma.telegraph.messages;

import com.Remma.telegraph.chats.ChatEntity;
import com.Remma.telegraph.chats.ChatService;
import com.Remma.telegraph.users.UserEntity;
import com.Remma.telegraph.users.UserService;
import com.Remma.telegraph.utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository repository;
    private final ChatService chatService;
    private final UserService userService;
    private final MessageMapper mapper;

    public MessageService(
            MessageRepository repository,
            ChatService chatService,
            UserService userService,
            MessageMapper mapper
    ) {
        this.repository = repository;
        this.chatService = chatService;
        this.userService = userService;
        this.mapper = mapper;
    }

    public MessageResponse createMessage(
            Long userId,
            MessageRequest request
    ) {
        UserEntity sender = userService.getCurrentUserEntity();
        UserEntity receiver = userService.getEntityById(userId);
        ChatEntity chat = chatService.getOrCreateChat(sender, receiver);
        var entityToSave = mapper.toEntity(
                request,
                chat,
                sender
        );
        return mapper.toResponse(repository.save(entityToSave));
    }

    public List<MessageResponse> getAllMessagesByUserId(Long userId) {
        UserEntity current = userService.getCurrentUserEntity();
        UserEntity other = userService.getEntityById(userId);
        ChatEntity chat = chatService.getOrCreateChat(current, other);

        List<MessageEntity> messages = repository.findAllByChatIdOrderBySentAtAsc(chat.getId());

        messages.stream()
                .filter(m -> !m.getSender().getId().equals(current.getId()) && m.getReadAt() == null)
                .forEach(m -> m.setReadAt(LocalDateTime.now()));
        repository.saveAll(messages);

        return messages.stream()
                .map(mapper::toResponse)
                .toList();
    }

    public MessageResponse updateMessage(MessageRequest request, Long id) {
        UserEntity current = userService.getCurrentUserEntity();
        var foundEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        if (!current.getId().equals(foundEntity.getSender().getId())) {
            throw new AccessDeniedException("Access denied");
        }

        foundEntity.setText(request.text());

        return mapper.toResponse(repository.save(foundEntity));
    }

    public void deleteMessage(Long id) {
        var foundEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        UserEntity current = userService.getCurrentUserEntity();
        if (!current.getId().equals(foundEntity.getSender().getId())) {
            throw new AccessDeniedException("Access denied");
        }
        repository.deleteById(id);
    }
}
