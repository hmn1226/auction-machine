package com.auctionmachine.core.data;

import java.time.Instant;

import com.auctionmachine.util.BeanUtil;

import lombok.Data;

@Data
public class PreBid {
	String auctionEntryId;
	String userId;
	Instant bidTime;
	Integer bidPrice;
	@Override
	public String toString() {
		return BeanUtil.describe(this);
	}
}
