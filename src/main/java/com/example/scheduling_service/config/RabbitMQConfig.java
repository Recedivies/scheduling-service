package com.example.scheduling_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@EnableRabbit
@Configuration
public class RabbitMQConfig {
//    public static final String TASK_DIRECT_EXCHANGE = "x.manja";
//    public static final String QUEUE_TASK = "q.maja";
//    public static final String QUEUE_TASK_RETRY = "q.task-retry";
//
//    public static final String ROUTING_KEY_TASK = "manja";
//    public static final String ROUTING_KEY_TASK_RETRY = "task-retry";

    public static final String QUEUE_NAME = "myQueue3";
    public static final String EXCHANGE_NAME = "myTopicExchange";
    public static final String ROUTING_KEY = "myRoutingKey.#";

//    @Bean
//    public ConnectionFactory connectionFactory() {
//        return new CachingConnectionFactory();
//    }
//    @Bean
//    public AmqpAdmin amqpAdmin() {
//        return new RabbitAdmin(connectionFactory());
//    }

    @Bean
    public Queue myQueue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange myExchange() {
        System.out.println("HELLO?");
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(myQueue()).to(myExchange()).with(ROUTING_KEY);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }
//    @Bean
//    public Declarables createTaskScheduleSchema() {
//        System.out.println("[DEBUG] HELLO DECLARE X & Q");
//        return new Declarables(
//                new DirectExchange(TASK_DIRECT_EXCHANGE),
//                new Queue(QUEUE_TASK),
//                new Queue(QUEUE_TASK_RETRY),
//                new Binding(QUEUE_TASK, Binding.DestinationType.QUEUE,
//                        TASK_DIRECT_EXCHANGE, ROUTING_KEY_TASK, null),
//                new Binding(QUEUE_TASK_RETRY, Binding.DestinationType.QUEUE,
//                        TASK_DIRECT_EXCHANGE, ROUTING_KEY_TASK_RETRY, null));
//    }

//    @Bean
//    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        return container;
//    }
}
