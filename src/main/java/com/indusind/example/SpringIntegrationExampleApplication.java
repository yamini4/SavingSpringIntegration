package com.indusind.example;

//import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableIntegration
@EnableScheduling
public class SpringIntegrationExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationExampleApplication.class, args);

	}

	// It will print what all are beans got loaded in this application
//	@Bean
//	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//		return args -> {
//			System.out.println("---- Loaded Beans ----");
//			for (String beanName : ctx.getBeanDefinitionNames()) {
//				if (beanName.contains("process")) {
//					System.out.println(beanName);
//				}
//			}
//		};
//	}

}
