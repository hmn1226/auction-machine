package com.auctionmachine.resources.service;

import java.time.Instant;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.auctionmachine.core.DataStore;
import com.auctionmachine.core.data.AuctionLane;
import com.auctionmachine.core.data.LiveBid;
import com.auctionmachine.core.data.PreBid;
import com.auctionmachine.resources.model.request.LiveBidRequest;
import com.auctionmachine.resources.model.request.PreBidRequest;

@Service
public class BidService {
	private DataStore dataStore =  DataStore.getInstance();
	public void preBid(PreBidRequest req) {
		AuctionLane auctionLane = this.dataStore.getAuctionLane(req);
		
		PreBid preBid = new PreBid();
		preBid.setBidUserId(req.getUserId());
		preBid.setAuctionEntryId(req.getAuctionEntryId());
		preBid.setBidTime(Instant.now());
		preBid.setBidPrice(req.getPreBidPrice());
		auctionLane.getCurrentAuctionEntry().getPreBidList().add(preBid);
 	}
	
	public void liveBid(LiveBidRequest req) {
		AuctionLane auctionLane = this.dataStore.getAuctionLane(req);
		if(req.getBidUserId()==null) {
			req.setBidUserId("ABC"+this.generateFiveDigitNumber());//TODO 仮			
		}
		LiveBid liveBid = new LiveBid();
		liveBid.setBidUserId(req.getBidUserId());
		liveBid.setAuctionEntryId(req.getAuctionEntryId());
		liveBid.setBidTime(Instant.now());
		auctionLane.getLiveBidQueue().add(liveBid);
 	}
	
	// 5桁のランダムな数字を生成する関数
    public int generateFiveDigitNumber() {
        Random random = new Random();
        return 10000 + random.nextInt(90000); // 10000～99999の範囲
    }
}
