package com.Remma.telegraph.messages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {
    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createMessage(
            @RequestBody MessageRequest request
    ) {
        log.info("Called method createMessage");
        return ResponseEntity.ok(messageService.createMessage(request));
    }

    @GetMapping("/sent-messages")
    public ResponseEntity<List<MessageResponse>> getAllSentMessages() {
        log.info("Called method getAllSentMessages");
        return ResponseEntity.ok(messageService.getAllSentMessages());
    }

    @GetMapping("/received-messages")
    public ResponseEntity<List<MessageResponse>> getAllReceivedMessages() {
        log.info("Called method getAllReceivedMessages");
        return ResponseEntity.ok(messageService.getAllReceivedMessages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getMessageById(
            @PathVariable(name = "id") Long id
    ) {
        log.info("Called method getMessageById, id = {}", id);
        return ResponseEntity.ok(messageService.getMessageById(id));
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
