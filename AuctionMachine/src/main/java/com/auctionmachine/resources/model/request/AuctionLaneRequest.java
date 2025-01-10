package com.auctionmachine.resources.model.request;

import jakarta.validation.constraints.NotNull;

import com.auctionmachine.lib.AuctionLaneStatus;
import com.auctionmachine.resources.model.AuctionLaneKey;
import com.auctionmachine.util.BeanUtil;

import lombok.Data;

@Data
public class AuctionLaneRequest implements AuctionLaneKey{
	private String auctionRoomId;
	private Integer auctionLaneId;
	@NotNull
	private AuctionLaneStatus auctionLaneStatus;
	
    @Override
    public String toString() {
        return BeanUtil.describe(this);
    }
}