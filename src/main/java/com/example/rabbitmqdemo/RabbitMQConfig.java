package com.example.rabbitmqdemo;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange; // Hoặc DirectExchange, FanoutExchange tùy nhu cầu
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${app.rabbitmq.queue-name}")
    private String queueName;

    @Value("${app.rabbitmq.exchange-name}")
    private String exchangeName;

    @Value("${app.rabbitmq.routing-key}")
    private String routingKey;

    // Bean để tạo Queue
    @Bean
    public Queue queue() {
        // new Queue(queueName, durable, exclusive, autoDelete, arguments)
        // durable = true: queue sẽ tồn tại sau khi RabbitMQ server restart
        return new Queue(queueName, true);
    }

    // Bean để tạo Exchange (Ở đây dùng TopicExchange, bạn có thể dùng DirectExchange hoặc FanoutExchange)
    // TopicExchange cho phép routing linh hoạt dựa trên pattern của routing key.
    // DirectExchange: message sẽ được route đến queue có binding key khớp chính xác với routing key của message.
    // FanoutExchange: message sẽ được route đến tất cả các queue đã bind với exchange này, bỏ qua routing key.
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    // Bean để tạo Binding giữa Queue và Exchange với một Routing Key
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    // Bạn cũng có thể thêm MessageConverter bean nếu muốn tùy chỉnh cách message được serialize/deserialize
    // Ví dụ, để gửi và nhận đối tượng JSON:
    /*
    import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
    import org.springframework.amqp.support.converter.MessageConverter;

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    */
}