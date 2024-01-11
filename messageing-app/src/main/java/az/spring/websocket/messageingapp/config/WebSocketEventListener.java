package az.spring.websocket.messageingapp.config;

import az.spring.websocket.messageingapp.enums.MessageType;
import az.spring.websocket.messageingapp.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = null;

        if(headerAccessor.getSessionAttributes() != null) {
            username = (String) headerAccessor.getSessionAttributes().get("username");
        }

        if(username != null) {
            log.info("Uder disconnected: {}", username);
            Message message = Message.builder()
                    .type(MessageType.DISCONNECT)
                    .sender(username)
                    .build();

            simpMessageSendingOperations.convertAndSend("/topic/public", message);
        }

    }
}
