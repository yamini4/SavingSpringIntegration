package com.indusind.example.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface FileGateway {

	@Gateway(requestChannel = "fileChannel")
	void triggerFileProcess();
}