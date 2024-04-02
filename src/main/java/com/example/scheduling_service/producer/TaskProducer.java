package com.example.scheduling_service.producer;

import com.example.scheduling_service.config.rabbit.RabbitMQConfig;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void processTaskSchedule(String jobId) throws AmqpException {
        System.out.println("[DEBUG] HELLO PRODUCT MESSAGE ? " + jobId);
        rabbitTemplate.convertAndSend(RabbitMQConfig.TASK_DIRECT_EXCHANGE, RabbitMQConfig.ROUTING_KEY_TASK, jobId);
        System.out.println("[DEBUG] Send msg = " + jobId);
    }
}
