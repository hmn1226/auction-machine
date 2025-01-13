package com.auctionmachine.resources.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auctionmachine.resources.model.request.AuctionLaneRequest;
import com.auctionmachine.resources.model.request.CurrentPriceRequest;
import com.auctionmachine.resources.model.response.CurrentEntry;
import com.auctionmachine.resources.model.response.CurrentLane;
import com.auctionmachine.resources.service.AuctionLaneService;

@RestController
public class AuctionLaneController {

	@Autowired
	private AuctionLaneService auctionLaneService;
	
	@PostMapping("/api/auction-lane/{auctionRoomId}/{auctionLaneId}/status")
	public void status(
	        @PathVariable("auctionRoomId") String auctionRoomId,
	        @PathVariable("auctionLaneId") Integer auctionLaneId,
	        @RequestBody AuctionLaneRequest auctionLaneRequest
	) {
		auctionLaneRequest.setAuctionRoomId(auctionRoomId);
		auctionLaneRequest.setAuctionLaneId(auctionLaneId);
	    this.auctionLaneService.status(auctionLaneRequest);
	}
	@PostMapping("/api/auction-lane/{auctionRoomId}/{auctionLaneId}/current-price")
	public void currentPrice(
	        @PathVariable("auctionRoomId") String auctionRoomId,
	        @PathVariable("auctionLaneId") Integer auctionLaneId,
	        @RequestBody CurrentPriceRequest currentPriceRequest
	) {
		currentPriceRequest.setAuctionRoomId(auctionRoomId);
		currentPriceRequest.setAuctionLaneId(auctionLaneId);
	    this.auctionLaneService.currentPrice(currentPriceRequest);
	}
	@PostMapping("/api/auction-lane/{auctionRoomId}/{auctionLaneId}/next-entry")
	public void nextEntry(
	        @PathVariable("auctionRoomId") String auctionRoomId,
	        @PathVariable("auctionLaneId") Integer auctionLaneId,
	        @RequestBody AuctionLaneRequest auctionLaneRequest
	) {
		auctionLaneRequest.setAuctionRoomId(auctionRoomId);
		auctionLaneRequest.setAuctionLaneId(auctionLaneId);
	    this.auctionLaneService.nextEntry(auctionLaneRequest);
	}
	@PostMapping("/api/auction-lane/{auctionRoomId}/{auctionLaneId}/prev-entry")
	public void prevEntry(
	        @PathVariable("auctionRoomId") String auctionRoomId,
	        @PathVariable("auctionLaneId") Integer auctionLaneId,
	        @RequestBody AuctionLaneRequest auctionLaneRequest
	) {
		auctionLaneRequest.setAuctionRoomId(auctionRoomId);
		auctionLaneRequest.setAuctionLaneId(auctionLaneId);
	    this.auctionLaneService.prevEntry(auctionLaneRequest);
	}
	@GetMapping("/api/auction-lane/{auctionRoomId}/{auctionLaneId}/current-entry")
	public CurrentEntry currentEntry(
		@PathVariable("auctionRoomId") String auctionRoomId,
		@PathVariable("auctionLaneId") Integer auctionLaneId,
		@RequestBody AuctionLaneRequest auctionLaneRequest
	) {
		auctionLaneRequest.setAuctionRoomId(auctionRoomId);
		auctionLaneRequest.setAuctionLaneId(auctionLaneId);
	    return this.auctionLaneService.currentEntry(auctionLaneRequest);
	}
	@GetMapping("/api/auction-lane/{auctionRoomId}/{auctionLaneId}/current-lane")
	public CurrentLane currentLane(
		@PathVariable("auctionRoomId") String auctionRoomId,
		@PathVariable("auctionLaneId") Integer auctionLaneId,
		@RequestBody AuctionLaneRequest auctionLaneRequest
	) {
		auctionLaneRequest.setAuctionRoomId(auctionRoomId);
		auctionLaneRequest.setAuctionLaneId(auctionLaneId);
	    return this.auctionLaneService.currentLane(auctionLaneRequest);
	}
	@PostMapping("/api/auction-lane/{auctionRoomId}/{auctionLaneId}/resolve-bids")
	public void resolveBids(
	        @PathVariable("auctionRoomId") String auctionRoomId,
	        @PathVariable("auctionLaneId") Integer auctionLaneId,
	        @RequestBody AuctionLaneRequest auctionLaneRequest
	) {
		auctionLaneRequest.setAuctionRoomId(auctionRoomId);
		auctionLaneRequest.setAuctionLaneId(auctionLaneId);
	    this.auctionLaneService.resolvePreBids(auctionLaneRequest);
	    this.auctionLaneService.resolveLiveBids(auctionLaneRequest);
	}

	@PostMapping("/api/auction-lane/{auctionRoomId}/{auctionLaneId}/resolve-live-bids")
	public void resolveLiveBids(
	        @PathVariable("auctionRoomId") String auctionRoomId,
	        @PathVariable("auctionLaneId") Integer auctionLaneId,
	        @RequestBody AuctionLaneRequest auctionLaneRequest
	) {
		auctionLaneRequest.setAuctionRoomId(auctionRoomId);
		auctionLaneRequest.setAuctionLaneId(auctionLaneId);
	    this.auctionLaneService.resolveLiveBids(auctionLaneRequest);
	}
	@PostMapping("/api/auction-lane/{auctionRoomId}/{auctionLaneId}/resolve-pre-bids")
	public void resolvePreBids(
	        @PathVariable("auctionRoomId") String auctionRoomId,
	        @PathVariable("auctionLaneId") Integer auctionLaneId,
	        @RequestBody AuctionLaneRequest auctionLaneRequest
	) {
		auctionLaneRequest.setAuctionRoomId(auctionRoomId);
		auctionLaneRequest.setAuctionLaneId(auctionLaneId);
	    this.auctionLaneService.resolvePreBids(auctionLaneRequest);
	}
	@PostMapping("/api/auction-lane/{auctionRoomId}/{auctionLaneId}/debug")
	public void debug(
	        @PathVariable("auctionRoomId") String auctionRoomId,
	        @PathVariable("auctionLaneId") Integer auctionLaneId,
	        @RequestBody AuctionLaneRequest auctionLaneRequest
	) {
		auctionLaneRequest.setAuctionRoomId(auctionRoomId);
		auctionLaneRequest.setAuctionLaneId(auctionLaneId);
	    this.auctionLaneService.debug(auctionLaneRequest);
	}
}