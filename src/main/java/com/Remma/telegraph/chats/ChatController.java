package com.Remma.telegraph.chats;

import com.Remma.telegraph.users.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private final ChatService chatService;

    public ChatController(
            ChatService chatService
    ) {
        this.chatService = chatService;
    }

    @GetMapping
    public ResponseEntity<List<ChatResponse>> getAllChats() {
        log.info("Called method getAllChats");
        return ResponseEntity.ok(chatService.getAllChats());
    }
}
