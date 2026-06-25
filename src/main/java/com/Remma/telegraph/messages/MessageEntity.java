package com.Remma.telegraph.messages;

import com.Remma.telegraph.users.UserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "sender_id")
    @ManyToOne
    private UserEntity sender;

    @JoinColumn(name = "receiver_id")
    @ManyToOne
    private UserEntity receiver;

    @Column
    private String text;

    @Column
    @CreationTimestamp
    private LocalDateTime sentAt;

    public MessageEntity() {}

    public MessageEntity(
            Long id,
            UserEntity sender,
            UserEntity receiver,
            String text,
            LocalDateTime sentAt
    ) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.sentAt = sentAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    public UserEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(UserEntity receiver) {
        this.receiver = receiver;
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
}
