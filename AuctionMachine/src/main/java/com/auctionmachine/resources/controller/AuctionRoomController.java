package com.auctionmachine.resources.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auctionmachine.core.data.AuctionRoomMonitor;
import com.auctionmachine.resources.model.request.AuctionRoomRequest;
import com.auctionmachine.resources.service.AuctionRoomService;

@RestController
public class AuctionRoomController {

	@Autowired
	private AuctionRoomService auctionRoomService;
	
	@GetMapping("/api/auction-room/{auctionRoomId}/monitor")
	public AuctionRoomMonitor monitor(
	        @PathVariable("auctionRoomId") String auctionRoomId,
	        @RequestBody AuctionRoomRequest auctionRoomRequest
	) {
		auctionRoomRequest.setAuctionRoomId(auctionRoomId);
	    return this.auctionRoomService.monitor(auctionRoomRequest);
	}
}