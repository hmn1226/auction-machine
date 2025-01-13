package com.auctionmachine.core;

import java.util.HashMap;
import java.util.Map;

import com.auctionmachine.core.data.AuctionEntry;
import com.auctionmachine.core.data.AuctionLane;
import com.auctionmachine.core.data.AuctionRoom;
import com.auctionmachine.resources.model.AuctionEntryKey;
import com.auctionmachine.resources.model.AuctionLaneKey;
import com.auctionmachine.util.AuctionRoomIdGenerator;

public class DataStore {
	
	private static final DataStore dataStore = new DataStore();
	
	private static final Map<String,AuctionRoom> auctionRoomMap = new HashMap<>();
	
	public static DataStore getInstance() {
		return dataStore;
	}
	public AuctionRoom addAuctionRoom() {
		String auctionRoomId = AuctionRoomIdGenerator.generateAuctionRoomId();
		return addAuctionRoom(auctionRoomId);
	}
	public AuctionRoom addAuctionRoom(String auctionRoomId) {
		if(!auctionRoomMap.containsKey(auctionRoomId)) {
			AuctionRoom auctionRoom = new AuctionRoom(auctionRoomId);
			auctionRoomMap.put(auctionRoomId,auctionRoom);
			return auctionRoom;
		}else {
			throw new IllegalArgumentException("auction room id:"+auctionRoomId+"is already exists.");
		}
	}
	public AuctionRoom getAuctionRoom(String auctionRoomId) {
		return auctionRoomMap.containsKey(auctionRoomId) ? auctionRoomMap.get(auctionRoomId) : null;
	}
	public AuctionLane getAuctionLane(String auctionRoomId,Integer auctionLaneId) {
		AuctionRoom auctionRoom = this.getAuctionRoom(auctionRoomId);
		if(auctionRoom!=null) {			
			Map<Integer,AuctionLane> map = auctionRoom.getAuctionLaneMap();
			return map.containsKey(auctionLaneId) ? map.get(auctionLaneId) : null;
		}
		return null;
	}
	public AuctionLane getAuctionLane(AuctionLaneKey key) {
		return getAuctionLane(key.getAuctionRoomId(),key.getAuctionLaneId());
	}
	public AuctionEntry getAuctionEntry(String auctionRoomId,Integer auctionLaneId,String auctionEntryId) {
		AuctionLane auctionLane = this.getAuctionLane(auctionRoomId, auctionLaneId);
		if(auctionLane!=null) {
			Map<String,AuctionEntry> map = auctionLane.getAuctionEntryMap();
			return map.containsKey(auctionEntryId) ? map.get(auctionEntryId) : null;
		}
		return null;
	}
	public AuctionEntry getAuctionEntry(AuctionEntryKey key) {
		return getAuctionEntry(key.getAuctionRoomId(),key.getAuctionLaneId(),key.getAuctionEntryId());
	}
	
}
