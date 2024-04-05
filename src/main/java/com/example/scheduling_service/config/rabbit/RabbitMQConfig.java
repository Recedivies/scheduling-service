package com.example.scheduling_service.config.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String TASK_DIRECT_EXCHANGE = "x.task";
    public static final String QUEUE_TASK = "q.task";
    public static final String QUEUE_TASK_RETRY = "q.task-retry";

    public static final String ROUTING_KEY_TASK = "task";
    public static final String ROUTING_KEY_TASK_RETRY = "task-retry";

    @Bean
    public Queue taskQueue() {
        return new Queue(QUEUE_TASK, false);
    }

    @Bean
    public Queue taskRetryQueue() {
        return new Queue(QUEUE_TASK_RETRY, false);
    }

    @Bean
    public DirectExchange exchangeTask() {
        return new DirectExchange(TASK_DIRECT_EXCHANGE);
    }

    @Bean
    public Binding bindingTask() {
        return BindingBuilder.bind(taskQueue()).to(exchangeTask()).with(ROUTING_KEY_TASK);
    }

    @Bean
    public Binding bindingTaskRetry() {
        return BindingBuilder.bind(taskRetryQueue()).to(exchangeTask()).with(ROUTING_KEY_TASK_RETRY);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }
}
