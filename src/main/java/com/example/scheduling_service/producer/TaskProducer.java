package com.example.scheduling_service.producer;

import com.example.scheduling_service.config.rabbit.RabbitMQConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    Logger logger = LoggerFactory.getLogger(getClass().getName());


    public void processTaskSchedule(String jobId, String jobType) throws AmqpException {
        rabbitTemplate.convertAndSend(RabbitMQConfig.TASK_DIRECT_EXCHANGE, "task."+jobType, jobId);
        logger.info("Send task = " + jobId);
    }
}
