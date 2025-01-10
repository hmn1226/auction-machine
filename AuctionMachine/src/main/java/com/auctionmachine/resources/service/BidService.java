package com.auctionmachine.resources.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.auctionmachine.core.DataStore;
import com.auctionmachine.core.data.AuctionLane;
import com.auctionmachine.core.data.LiveBid;
import com.auctionmachine.core.data.PreBid;
import com.auctionmachine.resources.model.request.LiveBidRequest;
import com.auctionmachine.resources.model.request.PreBidRequest;

@Service
public class BidService {

	DataStore dataStore =  DataStore.getInstance();

	public void preBid(PreBidRequest req) {
		AuctionLane auctionLane = this.dataStore.getAuctionLane(req);
		
		PreBid preBid = new PreBid();
		preBid.setUserId(req.getUserId());
		preBid.setAuctionEntryId(req.getAuctionEntryId());
		preBid.setBidTime(Instant.now());
		preBid.setBidPrice(req.getPreBidPrice());
		auctionLane.getCurrentAuctionEntry().getPreBidList().add(preBid);
 	}
	
	public void liveBid(LiveBidRequest req) {
		AuctionLane auctionLane = this.dataStore.getAuctionLane(req);
		
		LiveBid liveBid = new LiveBid();
		liveBid.setUserId(req.getUserId());
		liveBid.setAuctionEntryId(req.getAuctionEntryId());
		liveBid.setBidTime(Instant.now());
		auctionLane.getLiveBidQueue().add(liveBid);
 	}
}
