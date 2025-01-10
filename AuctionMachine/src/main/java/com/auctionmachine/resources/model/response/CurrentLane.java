package com.auctionmachine.resources.model.response;

import lombok.Data;

@Data
public class CurrentLane {
	Integer auctionLaneId;
	Integer bidInterval;
	
	String topPreBidUserId;
	Integer topPreBidPrice;
	String secondPreBidUserId;
	Integer secondPreBidPrice;
	
	String preBidRelflectUserId;
	Integer preBidReflectTimer;
	
	String auctionEntryId;
	String auctionEntryName;
	Integer currentPrice;
	String currentHolderUserId;
}

