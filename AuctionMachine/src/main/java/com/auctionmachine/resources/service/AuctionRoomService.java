package com.auctionmachine.resources.service;

import org.springframework.stereotype.Service;

import com.auctionmachine.core.DataStore;
import com.auctionmachine.core.data.AuctionRoom;
import com.auctionmachine.resources.model.request.AuctionRoomRequest;
import com.auctionmachine.resources.model.response.Monitor;

@Service
public class AuctionRoomService {
	DataStore dataStore = DataStore.getInstance();
	public Monitor monitor(AuctionRoomRequest req) {
		AuctionRoom auctionRoom = dataStore.getAuctionRoom(req.getAuctionRoomId());
		Monitor monitor = new Monitor();
		monitor.setHoge(auctionRoom.getAuctionRoomMonitor().toString());
		return monitor;
	}
}
