package com.auctionmachine.web.socket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

	private final SimpMessagingTemplate messagingTemplate;

	@Autowired
	public ChatController(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	@MessageMapping("/chat")
	public void sendMessage(ChatMessage message, @Header("roomId") String roomId) {
		System.out.println("Received message: " + message.getContent() + " in room: " + roomId);
		// 動的な送信先を指定
		messagingTemplate.convertAndSend("/topic/messages/" + roomId, message);
	}
}