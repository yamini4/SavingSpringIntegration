package com.indusind.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indusind.example.service.ProcessGateway;

@RestController
@RequestMapping("/api")
public class ProcessController {

	@Autowired
	private ProcessGateway gateway;

	@Autowired
	private MessageChannel inputChannel;

	@PostMapping("/send")
	public String sendMessage(@RequestBody String payload) {
		inputChannel.send(MessageBuilder.withPayload(payload).build());
		return "Message sent to inputChannel successfully!";
	}

	@PostMapping("/process")
	public String process(@RequestParam String name) {
		System.out.println("REST call received for name = " + name);
		String result = gateway.processName(name);
		System.out.println("Gateway result = " + result);
		return result;
	}

}
