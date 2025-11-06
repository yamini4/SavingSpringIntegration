package com.indusind.example.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@EnableIntegration
public class BasicIntegrationConfig {

	@Value("${INPUT_DIR}")
	private String INPUT_DIR;

	@Value("${OUTPUT_DIR}")
	private String OUTPUT_DIR;

	@Value("${FILE_PATTERN}")
	private String FILE_PATTERN;

	@Bean
	public MessageChannel fileChannel() {
		return new DirectChannel();
	}

	@Bean
	@InboundChannelAdapter(value = "fileChannel", poller = @Poller(fixedDelay = "1000"))
	// @InboundChannelAdapter(value = "fileChannel", poller = @Poller(cron = "0 0 10
	// * * *")) // Every day at 10 AM
	public MessageSource<File> fileReadingMessageSource() {
		System.out.println("=======calling fileChannel Integration : " + INPUT_DIR);
		FileReadingMessageSource sourceReader = new FileReadingMessageSource();
		sourceReader.setDirectory(new File(INPUT_DIR));
		sourceReader.setFilter(new SimplePatternFileListFilter(FILE_PATTERN));
		return sourceReader;
	}

	@Bean
	@ServiceActivator(inputChannel = "fileChannel")
	public MessageHandler fileWritingMessageHandler() {
		System.out.println("=======calling fileChannel Integration : " + OUTPUT_DIR);
		FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(OUTPUT_DIR));
		handler.setFileExistsMode(FileExistsMode.REPLACE);
		handler.setExpectReply(false);
		return handler;
	}
}