import React from "react";
import axios from "axios";

const StopButton = ({monitor})=>{

    const onStopButtonClick = ()=>{
        axios.post(`http://localhost:8080/api/auction-lane/${monitor.auctionRoomId}/${monitor.auctionLaneId}/status`, {
            auctionLaneStatus: "STOP",
        })
        .catch((error) => {
            console.error("エラー:", error);
        });
    }

    return (
        <button onClick={onStopButtonClick}>ストップ</button>
    );
};
export default StopButton;