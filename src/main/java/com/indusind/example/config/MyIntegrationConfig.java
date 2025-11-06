package com.indusind.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
public class MyIntegrationConfig {

	@Bean
	public IntegrationFlow processFlow() {
		return IntegrationFlow.from("inputChannel").<String, String>transform(payload -> {
			System.out.println("Received in Integration Flow: " + payload);
			return "Processed by Integration: " + payload.toUpperCase();
		}).get();
	}

	// Input channel for messages from REST controller
	@Bean
	public MessageChannel inputChannel() {
		return MessageChannels.direct().getObject();
	}
}
