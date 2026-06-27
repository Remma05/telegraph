package com.Remma.telegraph.messages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/a")
public class MessageController {
    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<MessageResponse> createMessage(
            @PathVariable(name = "userId") Long userId,
            @RequestBody MessageRequest request
    ) {
        log.info("Called method createMessage");
        return ResponseEntity.ok(messageService.createMessage(userId, request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<MessageResponse>> getAllMessagesByUserId(
            @PathVariable(name = "userId") Long userId
    ) {
        log.info("Called method getAllMessagesByUserId");
        return ResponseEntity.ok(messageService.getAllMessagesByUserId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateMessage(
            @RequestBody MessageRequest request,
            @PathVariable(name = "id") Long id
    ) {
        log.info("Called method updateMessage, id = {}", id);
        return ResponseEntity.ok(messageService.updateMessage(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(
            @PathVariable(name = "id") Long id
    ) {
        log.info("Called method deleteMessage, id = {}", id);
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}
