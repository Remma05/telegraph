package com.Remma.telegraph.messages;

import com.Remma.telegraph.chats.ChatEntity;
import com.Remma.telegraph.users.UserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private ChatEntity chat;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserEntity sender;

    @Column
    private String text;

    @Column
    @CreationTimestamp
    private LocalDateTime sentAt;

    @Column
    private LocalDateTime readAt;

    public MessageEntity() {}

    public MessageEntity(
            Long id,
            ChatEntity chat,
            UserEntity sender,
            String text,
            LocalDateTime sentAt
    ) {
        this.id = id;
        this.chat = chat;
        this.sender = sender;
        this.text = text;
        this.sentAt = sentAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChatEntity getChat() {
        return chat;
    }

    public void setChat(ChatEntity chat) {
        this.chat = chat;
    }

    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public LocalDateTime getReadAt() {
        return readAt;
    }

    public void setReadAt(LocalDateTime readAt) {
        this.readAt = readAt;
    }
}
