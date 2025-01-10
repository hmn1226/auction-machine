import React, { useEffect, useState } from "react";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";

const SOCKET_URL = "http://localhost:8080/gs-guide-websocket";

const useWebSocket = (roomId) => {
    const [stompClient, setStompClient] = useState(null);
    const [messages, setMessages] = useState([]);
    const [text1,setText1] = useState("");

    useEffect(() => {
        connectWebSocket();
        return () => disconnectWebSocket(); // Cleanup on unmount
    }, []);

    const connectWebSocket = () => {
        console.log("Opening WebSocket...");
    
        const socket = new SockJS(SOCKET_URL);
        const stomp = Stomp.over(socket);
    
        // 接続時にroomIdを渡す
        stomp.connect({ roomId }, () => {
        console.log("WebSocket connected!");
        stomp.subscribe(`/topic/messages/${roomId}`, (message) => {
            onMessageReceived(message);
        });
        });
    
        setStompClient(stomp);
    };
    
    const disconnectWebSocket = () => {
        if (stompClient) {
        stompClient.disconnect(() => {
            console.log("WebSocket disconnected!");
        });
        }
    };
    
    const onMessageReceived = (message) => {
        const newMessage = JSON.parse(message.body);
        setMessages((prevMessages) => [...prevMessages, newMessage]);
        console.log(newMessage.content);
        setText1(newMessage.content);
    };
    
    const sendMessage = () => {
        if (stompClient) {
        stompClient.send(
            `/app/chat`,
            { roomId: roomId }, // ヘッダーにroomIdを追加
            JSON.stringify({ content: "Hello from React!" })
        );
        } else {
        console.log("WebSocket is not connected.");
        }
    };

    return {
        messages,
        text1,
        sendMessage
    }
}
export default useWebSocket;