package com.example.scheduling_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@EnableRabbit
@Configuration
public class RabbitMQConfig {
    public static final String TASK_DIRECT_EXCHANGE = "x.task";
    public static final String QUEUE_TASK = "q.task";
    public static final String QUEUE_TASK_RETRY = "q.task-retry";

    public static final String ROUTING_KEY_TASK = "task";
    public static final String ROUTING_KEY_TASK_RETRY = "task-retry";

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory();
    }

    @Bean
    public RabbitAdmin admin(ConnectionFactory cf) {
        return new RabbitAdmin(cf);
    }

    @Bean
    public Declarables createTaskScheduleSchema() {
        System.out.println("[DEBUG] HELLO DECLARE X & Q");
        return new Declarables(
                new DirectExchange(TASK_DIRECT_EXCHANGE),
                new Queue(QUEUE_TASK),
                new Queue(QUEUE_TASK_RETRY),
                new Binding(QUEUE_TASK, Binding.DestinationType.QUEUE,
                        TASK_DIRECT_EXCHANGE, ROUTING_KEY_TASK, null),
                new Binding(QUEUE_TASK_RETRY, Binding.DestinationType.QUEUE,
                        TASK_DIRECT_EXCHANGE, ROUTING_KEY_TASK_RETRY, null));
    }
}
