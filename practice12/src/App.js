import React, { useEffect, useState } from "react";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";

const roomId = "room1";
const SOCKET_URL = `http://localhost:8080/chat`;

function App() {
  const [stompClient, setStompClient] = useState(null);
  const [messages, setMessages] = useState([]);

  useEffect(() => {
    const socket = new SockJS(SOCKET_URL);
    const stomp = Stomp.over(socket);

    stomp.connect({ roomId: roomId }, () => {
      console.log("WebSocket connected to room: " + roomId);
      stomp.subscribe(`/topic/messages/${roomId}`, (message) => {
        onMessageReceived(message);
      });
    });

    setStompClient(stomp);

    return () => {
      if (stompClient) {
        stompClient.disconnect(() => {
          console.log("WebSocket disconnected!");
        });
      }
    };
  }, []);

  const onMessageReceived = (message) => {
    const newMessage = JSON.parse(message.body);
    setMessages((prevMessages) => [...prevMessages, newMessage]);
  };

  const sendMessage = () => {
    if (stompClient) {
      stompClient.send(`/app/chat/${roomId}`, {}, JSON.stringify({ content: "Hello from React!" }));
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