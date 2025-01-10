import { useState, useEffect } from "react";
import SockJS from "sockjs-client";
import { over } from "stompjs";

// 型定義
type Message = {
  name: string;
  message: string;
};

// カスタムフック
export const useWebSocket = () => {
  const [stompClient, setStompClient] = useState<any>(null);
  const [messages, setMessages] = useState<string[]>([]);
  const [isConnected, setIsConnected] = useState(false);

  // WebSocket接続
  const connect = () => {
    const socket = new SockJS("http://localhost:8080/gs-guide-websocket");
    const stomp = over(socket);

    stomp.connect({}, (frame: any) => {
      console.log("Connected: " + frame);
      setIsConnected(true);

      stomp.subscribe("/topic/greetings", (greeting: any) => {
        const message = JSON.parse(greeting.body).content;
        setMessages((prevMessages) => [...prevMessages, message]);
      });
    }, (error) => {
        console.error('Connection error: ', error);
    });

    setStompClient(stomp);
  };

  // WebSocket切断
  const disconnect = () => {
    if (stompClient !== null) {
      stompClient.disconnect(() => {
        console.log("Disconnected");
        setIsConnected(false);
      });
    }
  };

  // メッセージ送信
  const sendMessage = (msg: Message) => {
    if (stompClient && isConnected) {
      stompClient.send("/app/hello", {}, JSON.stringify(msg));
    }
  };

  // 自動接続（3秒後）
  useEffect(() => {
    const timer = setTimeout(() => {
      connect();
    }, 3000);

    return () => clearTimeout(timer);
  }, []);

  // フックが返す値
  return { connect, disconnect, sendMessage, messages, isConnected };
};


