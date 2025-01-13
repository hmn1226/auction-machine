package com.auctionmachine.core.data;

import com.auctionmachine.lib.AuctionLaneStatus;

import lombok.Data;

@Data
public class AuctionLaneMonitor {
	int hoge;
	private String auctionRoomId;
	private Integer auctionLaneId;
	private String auctionEntryId;
	private String auctionEntryName;
	private Integer currentPrice;
	private String currentHolderUserId;
	private AuctionLaneStatus auctionLaneStatus;
}
