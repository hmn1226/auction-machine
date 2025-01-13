package com.auctionmachine.resources.service;

import org.springframework.stereotype.Service;

import com.auctionmachine.core.DataStore;
import com.auctionmachine.core.data.AuctionRoom;
import com.auctionmachine.core.data.AuctionRoomMonitor;
import com.auctionmachine.resources.model.request.AuctionRoomRequest;

@Service
public class AuctionRoomService {
	private DataStore dataStore = DataStore.getInstance();
	public AuctionRoomMonitor monitor(AuctionRoomRequest req) {
		AuctionRoom auctionRoom = dataStore.getAuctionRoom(req.getAuctionRoomId());
		return auctionRoom.getAuctionRoomMonitor();
	}
}
