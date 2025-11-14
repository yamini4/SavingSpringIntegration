package com.indusind.example.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SampleController {

	private final MessageChannel sampleInputChannel;

	private final MessagingTemplate messagingTemplate;

	public SampleController(MessagingTemplate messagingTemplate,
			@Qualifier("sampleInputChannel") MessageChannel sampleInputChannel) {
		this.messagingTemplate = messagingTemplate;
		this.sampleInputChannel = sampleInputChannel;
	}

	@PostMapping("/trigger")
	public ResponseEntity<String> triggerFlow(@RequestBody String payload) {
		try {
			// Send payload to the flow
			sampleInputChannel.send(MessageBuilder.withPayload(payload).build());
			messagingTemplate.convertAndSend("sampleFlow.input", payload);
			return ResponseEntity.ok("Flow executed successfully");
		} catch (Exception e) {
			// This block catches exceptions thrown before reaching errorChannel
			System.err.println("REST ERROR: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
		}
	}
}
