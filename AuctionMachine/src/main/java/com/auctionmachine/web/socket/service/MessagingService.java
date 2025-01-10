package com.auctionmachine.web.socket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessagingService {

    private static MessagingService instance;

    private final SimpMessagingTemplate messagingTemplate;

    // コンストラクタで Spring が自動的に DI する
    @Autowired
    public MessagingService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        instance = this;
    }

    // 静的なメソッドで MessagingService を取得できる
    public static MessagingService getInstance() {
        return instance;
    }

    // メッセージ送信メソッド
    public void sendMessage(String destination, Object message) {
        messagingTemplate.convertAndSend(destination, message);
    }
}