package com.indusind.example.config;

import java.io.File;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableIntegration
@EnableScheduling
public class BasicIntegrationConfig {

	@Value("${INPUT_DIR}")
	private String INPUT_DIR;

	@Value("${OUTPUT_DIR}")
	private String OUTPUT_DIR;

	@Value("${FILE_PATTERN}")
	private String FILE_PATTERN;

	// 1️⃣ Use ExecutorChannel instead of DirectChannel
	@Bean
	public MessageChannel fileChannel() {
		return new ExecutorChannel(Executors.newCachedThreadPool());
	}

	// 2️⃣ File Reader — polls every 1 minute
	@Bean
	@InboundChannelAdapter(value = "fileChannel", poller = @Poller(fixedDelay = "60000"))
	public MessageSource<File> fileReadingMessageSource() {

		System.out.println(">>> Poller invoked <<<");
		System.out.println("INPUT_DIR = " + INPUT_DIR);
		System.out.println("FILE_PATTERN = " + FILE_PATTERN);

		File dir = new File(INPUT_DIR);

		if (!dir.exists()) {
			System.err.println("❌ INPUT_DIR DOES NOT EXIST: " + dir.getAbsolutePath());
		}

		FileReadingMessageSource sourceReader = new FileReadingMessageSource();
		sourceReader.setDirectory(dir);
		sourceReader.setFilter(new SimplePatternFileListFilter(FILE_PATTERN));

		// safer option: detect even newly modified files
		sourceReader.setUseWatchService(true);

		return sourceReader;
	}

	// 3️⃣ File Writer — moves file to output directory
	@Bean
	@ServiceActivator(inputChannel = "fileChannel")
	public MessageHandler fileWritingMessageHandler() {

		File outDir = new File(OUTPUT_DIR);

		if (!outDir.exists()) {
			System.err.println("❌ OUTPUT_DIR DOES NOT EXIST, creating: " + outDir.getAbsolutePath());
			outDir.mkdirs();
		}

		FileWritingMessageHandler handler = new FileWritingMessageHandler(outDir);

		handler.setFileExistsMode(FileExistsMode.REPLACE);
		handler.setExpectReply(false);
		// handler.setDeleteSourceFiles(true);
		handler.setFileExistsMode(FileExistsMode.REPLACE);

		handler.setFileNameGenerator(message -> {
			File file = (File) message.getPayload();
			System.out.println(">>> Processing file: " + file.getAbsolutePath());
			return file.getName();
		});

		return handler;
	}
}
