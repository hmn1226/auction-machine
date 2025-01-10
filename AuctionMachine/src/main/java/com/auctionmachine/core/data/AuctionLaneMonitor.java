package com.auctionmachine.core.data;

import lombok.Data;

@Data
public class AuctionLaneMonitor {
	String auctionEntryId;
	String auctionEntryName;
	Integer currentPrice;
}
