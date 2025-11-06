package com.indusind.example.service;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.Gateway;

@MessagingGateway
public interface ProcessGateway {

	@Gateway(requestChannel = "inputChannel")
	String processName(String name);
}