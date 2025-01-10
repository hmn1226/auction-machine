import React, { useEffect, useState } from "react";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";

const SOCKET_URL = "http://localhost:8080/gs-guide-websocket";

function App() {
  const [stompClient, setStompClient] = useState(null);
  const [messages, setMessages] = useState([]);
  const roomId = "room1"; // 渡したいパラメータ

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

  return (
    <div>
      <h1>WebSocket Client</h1>
      <button onClick={sendMessage}>Send Message</button>
      <div>
        <h2>Messages:</h2>
        {messages.map((msg, index) => (
          <p key={index}>{msg.content}</p>
        ))}
      </div>
    </div>
  );
}

export default App;