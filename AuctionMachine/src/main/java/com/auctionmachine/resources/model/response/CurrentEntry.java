package com.auctionmachine.resources.model.response;

import lombok.Data;

@Data
public class CurrentEntry {
	String auctionEntryId;
	String auctionEntryName;
	Integer currentPrice;
	String currentHolderUserId;
}

