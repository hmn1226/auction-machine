package com.auctionmachine.resources.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auctionmachine.resources.model.request.LiveBidRequest;
import com.auctionmachine.resources.model.request.PreBidRequest;
import com.auctionmachine.resources.service.BidService;

@RestController
public class BidController {

	@Autowired
	private BidService bidService;
	
	@PostMapping("/api/pre-bid/{auctionRoomId}/{auctionLaneId}/{auctionEntryId}")
    public void preBid(
            @PathVariable("auctionRoomId") String auctionRoomId,
            @PathVariable("auctionLaneId") Integer auctionLaneId,
            @PathVariable("auctionEntryId") String auctionEntryId,
    		@RequestBody PreBidRequest preBidRequest
    ) {
		preBidRequest.setAuctionRoomId(auctionRoomId);
		preBidRequest.setAuctionLaneId(auctionLaneId);
		preBidRequest.setAuctionEntryId(auctionEntryId);
		this.bidService.preBid(preBidRequest);
    }
	
	@PostMapping("/api/live-bid/{auctionRoomId}/{auctionLaneId}/{auctionEntryId}")
    public void liveBid(
            @PathVariable("auctionRoomId") String auctionRoomId,
            @PathVariable("auctionLaneId") Integer auctionLaneId,
            @PathVariable("auctionEntryId") String auctionEntryId,
    		@RequestBody LiveBidRequest liveBidRequest
    ) {
		liveBidRequest.setAuctionRoomId(auctionRoomId);
		liveBidRequest.setAuctionLaneId(auctionLaneId);
		liveBidRequest.setAuctionEntryId(auctionEntryId);
		this.bidService.liveBid(liveBidRequest);
    }
}