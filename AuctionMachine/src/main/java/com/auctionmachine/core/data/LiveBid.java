package com.auctionmachine.core.data;

import java.time.Instant;

import com.auctionmachine.util.BeanUtil;

import lombok.Data;

@Data
public class LiveBid {
	private String auctionEntryId;
	private String bidUserId;
	private Instant bidTime;
	
    @Override
    public String toString() {
        return BeanUtil.describe(this);
    }
}
