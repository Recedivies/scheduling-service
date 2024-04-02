//package com.example.scheduling_service.config.rabbit;
//
//import org.springframework.amqp.core.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitDLConfig {
//    public static final String DLQ_TASK = "dlq.task";
//    public static final String DLX_TASK = "dlx.task";
//
//    @Bean
//    public Queue deadLetterQueue() {
//        return new Queue(DLQ_TASK, true); // Declare the DLQ as durable
//    }
//    @Bean
//    public DirectExchange deadLetterExchange() {
//        return new DirectExchange(DLX_TASK);
//    }
//
//    @Bean
//    public Binding deadLetterBinding() {
//        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DLQ_TASK);
//    }
//}
