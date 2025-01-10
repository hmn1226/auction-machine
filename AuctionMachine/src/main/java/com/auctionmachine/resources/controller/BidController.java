package com.auctionmachine.resources.controller;

import java.util.Random;

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
	BidService bidService;
	
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
		liveBidRequest.setUserId("ABC"+this.generateFiveDigitNumber());//TODO 仮
		this.bidService.liveBid(liveBidRequest);
    }
	
	// 5桁のランダムな数字を生成する関数
    public int generateFiveDigitNumber() {
        Random random = new Random();
        return 10000 + random.nextInt(90000); // 10000～99999の範囲
    }
}