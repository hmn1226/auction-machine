import React from "react";
import axios from "axios";

const StartButton = ({monitor})=>{

    const onStartButtonClick = ()=>{
        axios.post(`http://localhost:8080/api/auction-lane/${monitor.auctionRoomId}/${monitor.auctionLaneId}/status`, {
            auctionLaneStatus: "START",
        })
        .catch((error) => {
            console.error("エラー:", error);
        });
    }

    return (
        <button onClick={onStartButtonClick}>スタート</button>
    );
};
export default StartButton;