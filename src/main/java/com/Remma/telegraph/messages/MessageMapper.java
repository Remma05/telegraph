package com.Remma.telegraph.messages;

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
                entity.getSentAt()
        );
    }

    public MessageEntity toEntity(
            MessageRequest request,
            UserEntity sender,
            UserEntity receiver
    ) {
        MessageEntity entity = new MessageEntity();
        entity.setText(request.text());
        entity.setSender(sender);
        entity.setReceiver(receiver);
        return entity;
    }
}
