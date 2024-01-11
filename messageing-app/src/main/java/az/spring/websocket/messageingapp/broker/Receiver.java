package az.spring.websocket.messageingapp.broker;

import az.spring.websocket.messageingapp.model.Message;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
//@RequiredArgsConstructor
public class Receiver {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(Receiver.class);

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    private final SimpUserRegistry simpUserRegistry;

    public Receiver(SimpMessageSendingOperations simpMessageSendingOperations, SimpUserRegistry simpUserRegistry) {
        this.simpMessageSendingOperations = simpMessageSendingOperations;
        this.simpUserRegistry = simpUserRegistry;
    }

    @KafkaListener(topics = "messaging", groupId = "chat")
    public void consume(Message message) {
        logger.info(" Recevied Message from Kafka: " + message);
        for (SimpUser simpUser: simpUserRegistry.getUsers()) {
            for (SimpSession simpSession: simpUser.getSessions()) {
                if(!simpSession.getId().equals(message.getSessionId())) {
                    simpMessageSendingOperations.convertAndSendToUser(simpSession.getId(), "/topic/public", message);
                }
            }
        }
    }


}
