import React, { useEffect, useState } from "react";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";

const SOCKET_URL = "http://localhost:8080/gs-guide-websocket";

function App() {
  const [stompClient, setStompClient] = useState(null);
  const [isConnected, setIsConnected] = useState(false);
  const [messages, setMessages] = useState([]);

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

      stomp.subscribe("/topic/messages", (message) => {
        onMessageReceived(message);
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
    console.log("★"+message.body);
    setMessages((prevMessages) => [...prevMessages, newMessage]);
  };

  const sendMessage = () => {
    if (isConnected && stompClient) {
      stompClient.send("/app/chat", {}, JSON.stringify({ content: "Hello from React!" }));
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