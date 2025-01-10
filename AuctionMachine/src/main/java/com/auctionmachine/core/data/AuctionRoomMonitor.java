package com.auctionmachine.core.data;

import java.util.ArrayList;
import java.util.List;

import com.auctionmachine.util.BeanUtil;

import lombok.Data;

@Data
public class AuctionRoomMonitor {
	List<AuctionLaneMonitor> auctionLaneMonitors = new ArrayList<>();
	public String toString() {
		return BeanUtil.describe(this);
	}
}
