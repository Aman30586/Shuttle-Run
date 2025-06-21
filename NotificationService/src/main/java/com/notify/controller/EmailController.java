package com.notify.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.notify.configuration.MailConfig;
import com.notify.dto.Email;

@RestController
public class EmailController {

	@Autowired
	private RabbitTemplate template;
	
	@PostMapping("/sendEmail")
	public void sendEmail(@RequestBody Email email) {
//		template.convertAndSend("email-queue", email);
		template.convertAndSend(MailConfig.EXCHANGE,
				MailConfig.ROUTING_KEY, email);
	}
}
