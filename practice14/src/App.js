import React, { useEffect, useState } from "react";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";

const SOCKET_URL = "http://localhost:8080/gs-guide-websocket";

function App() {
  const [stompClient, setStompClient] = useState(null);
  const [isConnected, setIsConnected] = useState(false);
  const [messages, setMessages] = useState([]);
  const [privateMessages, setPrivateMessages] = useState([]);

  useEffect(() => {
    connectWebSocket();
    return () => disconnectWebSocket(); // Cleanup on unmount
  }, []);

  const connectWebSocket = () => {
    console.log("Opening WebSocket...");
    const socket = new SockJS(SOCKET_URL);
    const stomp = Stomp.over(socket);

    stomp.connect({}, () => {
      console.log("WebSocket connected!");
      setIsConnected(true);

      // 全体のメッセージ購読
      stomp.subscribe("/topic/messages/room1", (message) => {
        onMessageReceived(message);
      });

      // プライベートメッセージ購読
      stomp.subscribe("/user/topic/private", (message) => {
        onPrivateMessageReceived(message);
      });
    });

    setStompClient(stomp);
  };

  const disconnectWebSocket = () => {
    if (stompClient) {
      stompClient.disconnect(() => {
        console.log("WebSocket disconnected!");
        setIsConnected(false);
      });
    }
  };

  const onMessageReceived = (message) => {
    const newMessage = JSON.parse(message.body);
    console.log("Received public message: " + newMessage.content);
    setMessages((prevMessages) => [...prevMessages, newMessage]);
  };

  const onPrivateMessageReceived = (message) => {
    const newMessage = JSON.parse(message.body);
    console.log("Received private message: " + newMessage.content);
    setPrivateMessages((prevMessages) => [...prevMessages, newMessage]);
  };

  const sendMessage = () => {
    if (isConnected && stompClient) {
      stompClient.send(
        "/app/chat",
        { roomId: "room1" }, // ヘッダーに roomId を追加
        JSON.stringify({ content: "Bid placed from React!" })
      );
    } else {
      console.log("WebSocket is not connected.");
    }
  };

  const sendWinningMessage = () => {
    if (isConnected && stompClient) {
      stompClient.send("/app/auction/win", {}, JSON.stringify({ content: "You won the bid!" }));
    } else {
      console.log("WebSocket is not connected.");
    }
  };

  return (
    <div>
      <h1>WebSocket Client</h1>
      <button onClick={sendMessage}>Send Public Message (Bid)</button>
      <button onClick={sendWinningMessage}>Send Private Message (Winning Notification)</button>

      <div>
        <h2>Public Messages:</h2>
        {messages.map((msg, index) => (
          <p key={index}>{msg.content}</p>
        ))}
      </div>

      <div>
        <h2>Private Messages:</h2>
        {privateMessages.map((msg, index) => (
          <p key={index}>{msg.content}</p>
        ))}
      </div>
    </div>
  );
}

export default App;