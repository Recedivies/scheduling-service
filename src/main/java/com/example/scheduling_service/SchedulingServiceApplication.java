package com.example.scheduling_service;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableRabbit
public class SchedulingServiceApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(SchedulingServiceApplication.class).run(args);
		Environment environment = applicationContext.getEnvironment();
		System.out.println("[DEBUG]" + environment.getProperty("spring.datasource.url"));
		System.out.println("[DEBUG]" + environment.getProperty("spring.datasource.username"));
		System.out.println("[DEBUG]" + environment.getProperty("spring.datasource.password"));
		System.out.println("[DEBUG]" + environment.getProperty("spring.rabbitmq.host"));
	}

}
