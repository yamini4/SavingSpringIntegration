package com.indusind.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
public class ProcessGatewayService {

	@Autowired
	private MessageChannel inputChannel;

	public void sendMessage() {
		inputChannel.send(MessageBuilder.withPayload("Hello!").build());
	}
}
