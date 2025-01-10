package com.auctionmachine.resources.service;

import org.springframework.stereotype.Service;

import com.auctionmachine.core.DataStore;
import com.auctionmachine.core.data.AuctionEntry;
import com.auctionmachine.core.data.AuctionLane;
import com.auctionmachine.core.data.LiveBid;
import com.auctionmachine.resources.model.request.AuctionLaneRequest;
import com.auctionmachine.resources.model.response.CurrentEntry;
import com.auctionmachine.resources.model.response.CurrentLane;

@Service
public class AuctionLaneService {
	
	DataStore dataStore = DataStore.getInstance();

	public void status(AuctionLaneRequest request) {
		AuctionLane auctionLane = this.dataStore.getAuctionLane(request);
		auctionLane.setAuctionLaneStatus(request.getAuctionLaneStatus());
	}
	public void nextEntry(AuctionLaneRequest request) {
		AuctionLane auctionLane = this.dataStore.getAuctionLane(request);
		auctionLane.incrementPointer();
	}
	public void prevEntry(AuctionLaneRequest request) {
		AuctionLane auctionLane = this.dataStore.getAuctionLane(request);
		auctionLane.decrementPointer();
	}
	public CurrentEntry currentEntry(AuctionLaneRequest request) {
		AuctionLane auctionLane = this.dataStore.getAuctionLane(request);
		AuctionEntry auctionEntry = auctionLane.getCurrentAuctionEntry();
		
		CurrentEntry currentEntry = new CurrentEntry();
		currentEntry.setAuctionEntryId(auctionEntry.getAuctionEntryId());
		currentEntry.setAuctionEntryName(auctionEntry.getAuctionEntryName());
		currentEntry.setCurrentPrice(auctionEntry.getCurrentPrice());
		currentEntry.setCurrentHolderUserId(auctionEntry.getCurrentHolderUserId());
		return currentEntry;
	}
	public CurrentLane currentLane(AuctionLaneRequest request) {
		AuctionLane auctionLane = this.dataStore.getAuctionLane(request);

		CurrentLane currentLane = new CurrentLane();
		currentLane.setAuctionLaneId(auctionLane.getAuctionLaneId());
		currentLane.setBidInterval(auctionLane.getBidInterval());
		
		currentLane.setTopPreBidUserId(auctionLane.getTopPreBidUserId());
		currentLane.setTopPreBidPrice(auctionLane.getTopPreBidPrice());
		currentLane.setSecondPreBidUserId(auctionLane.getSecondPreBidUserId());
		currentLane.setSecondPreBidPrice(auctionLane.getSecondPreBidPrice());
		currentLane.setPreBidReflectTimer(auctionLane.getPreBidReflectTimer());
		currentLane.setPreBidRelflectUserId(auctionLane.getPreBidReflectUserId());
		
		AuctionEntry auctionEntry = auctionLane.getCurrentAuctionEntry();
		currentLane.setAuctionEntryId(auctionEntry.getAuctionEntryId());
		currentLane.setAuctionEntryName(auctionEntry.getAuctionEntryName());
		currentLane.setCurrentPrice(auctionEntry.getCurrentPrice());
		currentLane.setCurrentHolderUserId(auctionEntry.getCurrentHolderUserId());
		return currentLane;
	}
	public void resolveLiveBids(AuctionLaneRequest request) {
		AuctionLane auctionLane = this.dataStore.getAuctionLane(request);
		auctionLane.resolveLiveBids();
	}
	public void resolvePreBids(AuctionLaneRequest request) {
		AuctionLane auctionLane = this.dataStore.getAuctionLane(request);
		auctionLane.resolvePreBids();
	}
	public void debug(AuctionLaneRequest request) {
		AuctionLane auctionLane = this.dataStore.getAuctionLane(request);

		//---
		for(LiveBid liveBid : auctionLane.getLiveBidQueue()) {
			System.out.println(liveBid);
		}
	}
}
