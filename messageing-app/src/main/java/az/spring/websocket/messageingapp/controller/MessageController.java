package az.spring.websocket.messageingapp.controller;

import az.spring.websocket.messageingapp.broker.Sender;
import az.spring.websocket.messageingapp.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class MessageController {

    private final Sender sender;

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public MessageController(Sender sender, SimpMessageSendingOperations simpMessageSendingOperations) {
        this.sender = sender;
        this.simpMessageSendingOperations = simpMessageSendingOperations;
    }

    @MessageMapping("/chat/send-message")
    public void senMessage(@Payload Message message, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        System.out.println("simpMessageHeaderAccessor ==========" + simpMessageHeaderAccessor);
        message.setSessionId(simpMessageHeaderAccessor.getSessionId());
        sender.send("messaging", message);
        log.info("Sending message to /topic/public: " + message);
        simpMessageSendingOperations.convertAndSend("/topic/public", message);
        log.info("Message sent to /topic/public: " + message);
    }

    @MessageMapping("/chat/add-user")
    @SendTo("/topic/public")
    public Message addUser(@Payload Message message, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        if(simpMessageHeaderAccessor.getSessionAttributes() != null) {
            simpMessageHeaderAccessor.getSessionAttributes().put("username", message.getSender());
        }
        return message;
    }
}
