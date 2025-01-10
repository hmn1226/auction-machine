import React, { useEffect, useState } from "react";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";
import useWebSocket from "../hooks/useWebSocket";


const SOCKET_URL = "http://localhost:8080/gs-guide-websocket";

const WebSocket = ()=>{
    const roomId = "room1";
    const {messages,text1,sendMessage} = useWebSocket(roomId);

    return (
        <>
            <button onClick={sendMessage}>Send Message</button>
            <div>{text1}</div>
            <div>
                <h2>Messages:</h2>
                {messages.map((msg, index) => (
                <p key={index}>{msg.content}</p>
                ))}
            </div>
        </>
    );
}
export default WebSocket;