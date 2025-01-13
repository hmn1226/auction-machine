import React, { useEffect, useState } from "react";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";
import useWebSocket from "../hooks/useWebSocket";
import StartButton from "./StartButton";
import StopButton from "./StopButton";
import CurrentPriceButton from "./CurrentPriceButton";
import AuctionLaneStatus from "./AuctionLaneStatus";
import BidButton from "./LiveBidButton";

const SOCKET_URL = "http://localhost:8080/gs-guide-websocket";

const WebSocket = ()=>{
    const roomId = "room1";
    const {wsObj,sendMessage} = useWebSocket(roomId);

    return (
        <>
            <button onClick={sendMessage}>Send Message</button>
            <div style={{ border: "1px solid #F00" }}>
            {wsObj?.auctionLaneMonitors?.map((monitor, index) => (
                <div key={index}>
                <span>{monitor.hoge}    </span>
                <span>{monitor.auctionEntryId}  </span>
                <span>{monitor.auctionEntryName}    </span>
                <span>{monitor.currentPrice}    </span>
                <span>{monitor.currentHolderUserId} </span>
                
                <AuctionLaneStatus monitor={monitor}/>
                {/* auctionLaneStatus に応じてボタンを切り替え */}
                {monitor.auctionLaneStatus !== "START" && <StartButton monitor={monitor} />}
                {monitor.auctionLaneStatus !== "STOP" && <StopButton monitor={monitor} />}

                <CurrentPriceButton monitor={monitor} />
                <BidButton monitor={monitor} />
                
                </div> 

            ))}
            </div>
        </>
    );
}
export default WebSocket;