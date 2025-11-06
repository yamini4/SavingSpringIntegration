package com.indusind.example.config;

//import java.io.File;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.integration.dsl.IntegrationFlow;
//import org.springframework.integration.dsl.Pollers;
//import org.springframework.integration.file.dsl.Files;

// @Configuration
public class FileIntegrationConfig {

//	@Bean
//	public IntegrationFlow fileMoveFlow() {
//		return IntegrationFlow
//				.from(Files.inboundAdapter(new File("src/main/resources/input")).patternFilter("*.txt"),
//						e -> e.poller(Pollers.fixedDelay(5000)))
//				.handle(Files.outboundAdapter(new File("src/main/resources/output")).autoCreateDirectory(true)).get();
//	}
}
