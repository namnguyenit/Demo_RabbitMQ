package com.example.rabbitmqdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageProducer messageProducer;

    @Autowired
    public MessageController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestParam("message") String message) {
        messageProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent to RabbitMQ: " + message);
    }

    // Endpoint để gửi CustomMessage (nếu bạn đã implement)
    /*
    @PostMapping("/send-custom")
    public ResponseEntity<String> sendCustomMessage(@RequestBody CustomMessage customMessage) {
        messageProducer.sendCustomMessage(customMessage);
        return ResponseEntity.ok("Custom message sent to RabbitMQ: " + customMessage.toString());
    }
    */
}