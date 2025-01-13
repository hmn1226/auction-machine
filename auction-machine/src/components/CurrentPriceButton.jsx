import React, { useState } from "react";
import axios from "axios";

const CurrentPriceButton = ({monitor})=>{

    const[currentPrice,setCurrentPrice] = useState(1000);

    const onCurrentPriceButtonClick = ()=>{
        axios.post(`http://localhost:8080/api/auction-lane/${monitor.auctionRoomId}/${monitor.auctionLaneId}/current-price`, {
            currentPrice : currentPrice
        })
        .catch((error) => {
            console.error("エラー:", error);
        });   
    }

    return (
        <span>
            <input type="text" 
                value={currentPrice} 
                onChange={(e) => setCurrentPrice(e.target.value)}
                style={{
                    width:"50px",height:"24px",textAlign:"right",fontWeight:"bold"
                }}
            />
            <button onClick={onCurrentPriceButtonClick}>現在価格変更</button>
        </span>
    );
};
export default CurrentPriceButton;