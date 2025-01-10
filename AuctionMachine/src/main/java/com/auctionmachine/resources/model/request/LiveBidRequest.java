package com.auctionmachine.resources.model.request;

import com.auctionmachine.resources.model.AuctionEntryKey;
import com.auctionmachine.resources.model.AuctionLaneKey;
import com.auctionmachine.util.BeanUtil;

import lombok.Data;

@Data
public class LiveBidRequest implements AuctionLaneKey,AuctionEntryKey{
	private String auctionRoomId;
	private Integer auctionLaneId;
	private String auctionEntryId;
    private String userId;

    @Override
    public String toString() {
        return BeanUtil.describe(this);
    }
}