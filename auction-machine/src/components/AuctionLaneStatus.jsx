import React from "react";

const AuctionLaneStatus = ({monitor})=>{
    let color;
    switch(monitor.auctionLaneStatus){
        case "STOP" : color="black"; break;
        case "START" : color="blue"; break;
    }

    return (
        <span
            style={{backgroundColor:color,color:"white",padding:"6px 10px"}}
        >
            {monitor.auctionLaneStatus}
        </span>
    );
};
export default AuctionLaneStatus;