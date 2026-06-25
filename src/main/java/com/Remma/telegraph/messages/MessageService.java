package com.Remma.telegraph.messages;

import com.Remma.telegraph.users.UserEntity;
import com.Remma.telegraph.users.UserService;
import com.Remma.telegraph.utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository repository;
    private final UserService userService;
    private final MessageMapper mapper;
    private final SecurityUtils securityUtils;

    public MessageService(
            MessageRepository repository, UserService userService,
            MessageMapper mapper,
            SecurityUtils securityUtils
    ) {
        this.repository = repository;
        this.userService = userService;
        this.mapper = mapper;
        this.securityUtils = securityUtils;
    }

    public MessageResponse createMessage(
            MessageRequest request
    ) {
        UserEntity sender = securityUtils.getCurrentUser();
        UserEntity receiver = userService.getEntityById(request.receiverId());
        var entityToSave = mapper.toEntity(
                request,
                sender,
                receiver
        );
        return mapper.toResponse(repository.save(entityToSave));
    }

    public List<MessageResponse> getAllSentMessages() {
        UserEntity sender = securityUtils.getCurrentUser();
        return repository.findAllBySenderId(sender.getId())
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public List<MessageResponse> getAllReceivedMessages() {
        UserEntity receiver = securityUtils.getCurrentUser();
        return repository.findAllByReceiverId(receiver.getId())
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public MessageResponse getMessageById(Long id) {
        var foundEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        return mapper.toResponse(foundEntity);
    }

    public MessageResponse updateMessage(MessageRequest request, Long id) {
        var foundEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        foundEntity.setText(request.text());

        return mapper.toResponse(repository.save(foundEntity));
    }

    public void deleteMessage(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found");
        }

        repository.deleteById(id);
    }
}
