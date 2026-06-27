package com.Remma.telegraph.messages;

import com.Remma.telegraph.chats.ChatEntity;
import com.Remma.telegraph.users.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
    public MessageResponse toResponse(
            MessageEntity entity
    ) {
        return new MessageResponse(
                entity.getText(),
                entity.getSender().getUsername(),
                entity.getSentAt(),
                entity.getReadAt()
        );
    }

    public MessageEntity toEntity(
            MessageRequest request,
            ChatEntity chat,
            UserEntity sender
    ) {
        MessageEntity entity = new MessageEntity();
        entity.setText(request.text());
        entity.setChat(chat);
        entity.setSender(sender);
        return entity;
    }
}
