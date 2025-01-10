package com.auctionmachine.web.socket.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class AdminWebSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	System.out.println("Received message: " + message.getPayload());

        // TODO: ここでControllerにメッセージを渡すようにする
        session.sendMessage(new TextMessage("Message received: " + message.getPayload()));
    }
}