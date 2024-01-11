package az.spring.websocket.messageingapp.model;

import az.spring.websocket.messageingapp.enums.MessageType;
import lombok.Data;

@Data
public class Message {

    private MessageType type;
    private String content;
    private String sender;
    private String sessionId;
}
