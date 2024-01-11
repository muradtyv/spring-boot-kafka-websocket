package az.spring.websocket.messageingapp.controller;

import az.spring.websocket.messageingapp.model.Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommandController {

    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public CommandController(KafkaTemplate<String, Message> kafkaTemplate, SimpMessageSendingOperations simpMessageSendingOperations) {
        this.kafkaTemplate = kafkaTemplate;
        this.simpMessageSendingOperations = simpMessageSendingOperations;
    }

    @PostMapping("/send")
    public void send(@RequestBody Message message) {
        kafkaTemplate.send("messaging", message);
        simpMessageSendingOperations.convertAndSend("/topic/public", message);
    }
}
