package com.auctionmachine.core;

import java.util.HashMap;
import java.util.Map;

import com.auctionmachine.core.thread.AuctionLaneThread;

public class AuctionLaneThreadStore {
	private static final Map<String,Map<Integer,AuctionLaneThread>> auctionLaneThreadMap = new HashMap<>();
	
	public static void put(String auctionRoomId,int auctionLaneId,AuctionLaneThread auctionLaneThread) {
		if(auctionLaneThreadMap.containsKey(auctionRoomId)){
			Map<Integer,AuctionLaneThread> map = auctionLaneThreadMap.get(auctionRoomId);
			if(map.containsKey(auctionLaneId)) {
				AuctionLaneThread _auctionLaneThread = map.get(auctionLaneId);
				_auctionLaneThread.destroy();
			}
			map.put(auctionLaneId, auctionLaneThread);
		}else {
			Map<Integer,AuctionLaneThread> map = new HashMap<>();
			map.put(auctionLaneId,auctionLaneThread);
			auctionLaneThreadMap.put(auctionRoomId,map);
		}
	}
	public static AuctionLaneThread get(String auctionRoomId,int auctionLaneId) {
		if(auctionLaneThreadMap.containsKey(auctionRoomId)) {
			Map<Integer,AuctionLaneThread> map = auctionLaneThreadMap.get(auctionRoomId);
			if(map.containsKey(auctionLaneId)) {
				return map.get(auctionLaneId);
			}
		}
		return null;
	}
}
