package com.auctionmachine.core.data;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import com.auctionmachine.core.AuctionLaneThreadStore;
import com.auctionmachine.core.AuctionRoomThreadStore;
import com.auctionmachine.core.thread.AuctionLaneThread;
import com.auctionmachine.core.thread.AuctionRoomThread;
import com.auctionmachine.lib.AuctionBidUpDownType;
import com.auctionmachine.lib.AuctionProcessingType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "auctionRoomId")
public class AuctionRoom {

	private String auctionRoomId;
	private AuctionProcessingType auctionProcessingType = AuctionProcessingType.PARALLEL;
	private AuctionBidUpDownType auctionBidUpDownType = AuctionBidUpDownType.BID_UP;
	private boolean isDisplayCurrentPrice = true;
	private boolean isPublicAuction = true;
	private Instant publishStartTime = Instant.now();
	private Instant auctionStartTime = Instant.now();
	private long auctionTime;
	private final static long defaultAuctionTime = 60*60;// 60min
	private Map<Integer,AuctionLane> auctionLaneMap = new HashMap<>();
	private AuctionRoomMonitor auctionRoomMonitor = new AuctionRoomMonitor();
	
	public AuctionRoom(String auctionRoomId) {
		this(auctionRoomId,Instant.now(),defaultAuctionTime);		
	}
	public AuctionRoom(String auctionRoomId,Instant auctionStartTime) {
		this(auctionRoomId,auctionStartTime,defaultAuctionTime);		
	}
	public AuctionRoom(String auctionRoomId,Instant auctionStartTime,long auctionTime) {
		this.auctionRoomId = auctionRoomId;
		this.auctionStartTime = auctionStartTime;
		this.auctionTime = auctionTime;
		
		AuctionRoomThread auctionRoomThread = new AuctionRoomThread(this);
		AuctionRoomThreadStore.put(auctionRoomId, auctionRoomThread);
		auctionRoomThread.start();
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
		AuctionLaneThreadStore.put(auctionRoomId, auctionLaneId, auctionLaneThread);
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
