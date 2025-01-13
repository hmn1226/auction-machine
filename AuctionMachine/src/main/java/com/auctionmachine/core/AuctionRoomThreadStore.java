package com.auctionmachine.core;

import java.util.HashMap;
import java.util.Map;

import com.auctionmachine.core.thread.AuctionRoomThread;

public class AuctionRoomThreadStore {
	
	private static final Map<String,AuctionRoomThread> auctionRoomThreadMap = new HashMap<>();
	
	public static void put(String auctionRoomId,AuctionRoomThread auctionRoomThread) {
		if(auctionRoomThreadMap.containsKey(auctionRoomId)){
			AuctionRoomThread thread = auctionRoomThreadMap.get(auctionRoomId);
			thread.destroy();
		}
		auctionRoomThreadMap.put(auctionRoomId, auctionRoomThread);
	}
	public static AuctionRoomThread get(String auctionRoomId) {
		if(auctionRoomThreadMap.containsKey(auctionRoomId)) {
			return auctionRoomThreadMap.get(auctionRoomId);
		}
		return null;
	}
}
