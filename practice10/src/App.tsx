import React, { useEffect } from 'react';
import SockJS from 'sockjs-client';
import { over } from 'stompjs';

const SOCKET_URL = 'http://localhost:8080/gs-guide-websocket';

function App() {
  useEffect(() => {
    // SockJSのインスタンスを作成
    const socket = new SockJS(SOCKET_URL);
    const stompClient = over(socket);

    stompClient.connect({}, () => {
      console.log('Connected to WebSocket server');
    });

    return () => {
      stompClient.disconnect(() => {
        console.log('Disconnected');
      });
    };
  }, []);

  return (
    <div>
      <h1>WebSocket Connection Test</h1>
    </div>
  );
}

export default App;