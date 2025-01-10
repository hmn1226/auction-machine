package com.auctionmachine.core.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auctionmachine.core.ThreadStore;
import com.auctionmachine.core.thread.AuctionLaneThread;
import com.auctionmachine.lib.AuctionBidUpDownType;
import com.auctionmachine.lib.AuctionProcessingType;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
public class AuctionRoom {
	String auctionRoomId;
	AuctionProcessingType auctionProcessingType = AuctionProcessingType.PARALLEL;
	AuctionBidUpDownType auctionBidUpDownType = AuctionBidUpDownType.BID_UP;
	Boolean isDisplayCurrentPrice = true;
	Boolean isPublicAuction = true;
	Date publishStartTime = new Date();
	Date auctionStartTime = new Date();
	long auctionTime = 60*60;// 60min
	@JsonManagedReference
	Map<Integer,AuctionLane> auctionLaneMap = new HashMap<>();
	AuctionRoomMonitor auctionRoomMonitor = new AuctionRoomMonitor();
	
	public AuctionRoom(String auctionRoomId) {
		this.auctionRoomId = auctionRoomId;
	}
	public AuctionRoom(String auctionRoomId,Date auctionStartTime) {
		this.auctionRoomId = auctionRoomId;
		this.auctionStartTime = auctionStartTime;
	}
	public AuctionRoom(String auctionRoomId,Date auctionStartTime,long auctionTime) {
		this.auctionRoomId = auctionRoomId;
		this.auctionStartTime = auctionStartTime;
		this.auctionTime = auctionTime;
	}
	public AuctionLane addAuctionLane() {
		//AuctionLaneのオブジェクトを登録
		int auctionLaneId = this.getMaxAuctionLaneId()+1;
		AuctionLane auctionLane = new AuctionLane(this,auctionLaneId);
		//モニタオブジェクト追加
		AuctionLaneMonitor auctionLaneMonitor = new AuctionLaneMonitor();
		auctionLane.setAuctionLaneMonitor(auctionLaneMonitor);
		this.auctionRoomMonitor.getAuctionLaneMonitors().add(auctionLaneMonitor);
		//AuctionLaneMapへ登録
		this.auctionLaneMap.put(auctionLaneId,auctionLane);
		
		//AuctionLane監視用Threadを起動
		AuctionLaneThread auctionLaneThread = new AuctionLaneThread(auctionLane);
		ThreadStore.put(auctionRoomId, auctionLaneId, auctionLaneThread);
		auctionLaneThread.start();
		
		return auctionLane;
	}
	private int getMaxAuctionLaneId() {
		int maxAuctionLaneId = 0;
		for(Integer auctionLaneId : this.auctionLaneMap.keySet()) {
			maxAuctionLaneId = auctionLaneId>maxAuctionLaneId ? auctionLaneId : maxAuctionLaneId;
		}
		return maxAuctionLaneId;
	}
}
