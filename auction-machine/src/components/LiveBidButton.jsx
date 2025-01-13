import React, { useState } from "react";
import axios from "axios";

const BidButton = ({monitor}) => {

  const [bidUserId,setBidUserId] = useState("hmn1226");

  const onBidButtonClick = () => {
    axios.post(`http://localhost:8080/api/live-bid/${monitor.auctionRoomId}/${monitor.auctionLaneId}/${monitor.auctionEntryId}`, {
      bidUserId: bidUserId
    })
    .catch((error) => {
      console.error("エラー:", error);
    });
  };

  return (
    <span>
      <input type="text" value={bidUserId}
        onChange={(e)=>{setBidUserId(e.target.value)}}
        style={{ width:"80px",fontWeight:"bold" }}/>
      <button onClick={onBidButtonClick}>
        入札する
      </button>
    </span>
  );
};

export default BidButton;