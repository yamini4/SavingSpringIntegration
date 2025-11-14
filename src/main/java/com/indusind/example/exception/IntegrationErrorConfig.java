package com.indusind.example.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@Configuration
public class IntegrationErrorConfig {

	private static final Logger logger = LoggerFactory.getLogger(IntegrationErrorConfig.class);

	@Bean
	@ServiceActivator(inputChannel = "errorChannel")
	public MessageHandler globalErrorHandler() {
		return message -> {
			MessagingException exception = (MessagingException) message.getPayload();
			logger.error("GLOBAL ERROR: {}", exception.getMessage(), exception);
			logger.error("FAILED MESSAGE: {}", exception.getFailedMessage());
		};
	}

	@Bean
	public IntegrationFlow sampleFlow() {
		return f -> f.handle(m -> {
			throw new RuntimeException("Something went wrong!-------------------");
		});
	}

	@Bean
	public MessagingTemplate messagingTemplate() {
		return new MessagingTemplate();
	}
}
