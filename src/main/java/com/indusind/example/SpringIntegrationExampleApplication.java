package com.indusind.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.indusind.example")
public class SpringIntegrationExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationExampleApplication.class, args);

	}

	// 2 main methods means, it won't through error. It wont execute also...String[]
	// args one only will execute here.
	public static void main(Character[] args) {
		System.out.println("character=======================================");
	}

	// It will print what all are beans got loaded in this application
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			System.out.println("---- Loaded Beans ----");
			for (String beanName : ctx.getBeanDefinitionNames()) {
				if (beanName.contains("process")) {
					System.out.println(beanName);
				}
			}
		};
	}

}
