package com.example.rabbitmqdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    // Annotation @RabbitListener sẽ lắng nghe trên queue được định nghĩa trong application.properties
    // thông qua SpEL (Spring Expression Language)
    @RabbitListener(queues = "${app.rabbitmq.queue-name}")
    public void receiveMessage(String message) { // Kiểu dữ liệu của message phải khớp với kiểu bạn gửi
        LOGGER.info(String.format("Received message <- %s", message));
        // Xử lý message ở đây
    }

    // Nếu bạn gửi đối tượng CustomMessage và đã cấu hình Jackson2JsonMessageConverter
    /*
    @RabbitListener(queues = "${app.rabbitmq.queue-name}")
    public void receiveCustomMessage(CustomMessage message) {
        LOGGER.info(String.format("Received custom message <- %s", message.toString()));
        // Xử lý CustomMessage ở đây
    }
    */
}