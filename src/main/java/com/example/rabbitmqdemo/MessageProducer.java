package com.example.rabbitmqdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);

    @Value("${app.rabbitmq.exchange-name}")
    private String exchangeName;

    @Value("${app.rabbitmq.routing-key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        // Nếu bạn đã cấu hình jsonMessageConverter ở RabbitMQConfig,
        // bạn có thể inject và set nó ở đây nếu chưa được tự động cấu hình:
        // this.rabbitTemplate.setMessageConverter(jsonMessageConverter());
    }

    public void sendMessage(String message) {
        LOGGER.info(String.format("Sending message -> %s", message));
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }

    // Ví dụ gửi một đối tượng (cần có Jackson2JsonMessageConverter được cấu hình)
    /*
    public void sendCustomMessage(CustomMessage customMessage) {
        LOGGER.info(String.format("Sending custom message -> %s", customMessage.toString()));
        rabbitTemplate.convertAndSend(exchangeName, routingKey, customMessage);
    }
    */
}

// Nếu bạn muốn gửi đối tượng, tạo class CustomMessage:
/*
package com.example.rabbitmqdemo;

public class CustomMessage {
    private String id;
    private String content;

    // Constructors, getters, setters, toString()
    public CustomMessage() {}

    public CustomMessage(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    @Override
    public String toString() {
        return "CustomMessage{" +
               "id='" + id + '\'' +
               ", content='" + content + '\'' +
               '}';
    }
}
*/