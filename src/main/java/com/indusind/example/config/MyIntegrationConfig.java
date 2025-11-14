package com.indusind.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableIntegration
public class MyIntegrationConfig {

//	@Bean
//	public IntegrationFlow processFlow() {
//		return IntegrationFlow.from("inputChannel").<String, String>transform(payload -> {
//			System.out.println("Received in Integration Flow: " + payload);
//			return "Processed by Integration: " + payload.toUpperCase();
//		}).get();
//	}

	@Bean
	public IntegrationFlow processFlow() {
		return IntegrationFlow.from("inputChannel").<String, String>transform(String::toUpperCase)
				.channel("outputChannel").get();
	}

	@Bean
	public IntegrationFlow outputFlow() {
		return IntegrationFlow.from("outputChannel")
				.handle(msg -> System.out.println("Received in outputFlow: " + msg.getPayload())).get();
	}

	// Input channel for messages from REST controller
	@Bean
	public MessageChannel inputChannel() {
		return MessageChannels.direct().getObject();
	}

	@Bean
	public Runnable debugScheduler() {
		return new Runnable() {
			@Scheduled(fixedRate = 60000)
			public void test() {
				System.out.println(">>> Scheduler heartbeat <<<");
			}

			@Override
			public void run() {
				System.out.println("inside Run");

			}
		};
	}
}
