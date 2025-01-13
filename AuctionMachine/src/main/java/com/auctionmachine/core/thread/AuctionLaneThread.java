package com.auctionmachine.core.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auctionmachine.core.data.AuctionLane;
import com.auctionmachine.lib.AuctionLaneStatus;

public class AuctionLaneThread extends Thread{
	
	private Logger logger = LoggerFactory.getLogger(super.getClass());
	
	private AuctionLane auctionLane;
	private boolean isDestroy=false;
	
	private Integer loopInterval = 100;
	
	public AuctionLaneThread(AuctionLane auctionLane) {
		this.auctionLane = auctionLane;
	}
	
	@Override
	public void run() {
		this.auctionLaneLoop();
	}
	
	private void auctionLaneLoop(){
		while(!isDestroy) {
			try {
				if(this.auctionLane.getAuctionLaneStatus() == AuctionLaneStatus.START) {					
					auctionLane.resolvePreBids();
					auctionLane.resolveLiveBids();
				}
				super.sleep(this.loopInterval);
			}catch(Exception e) {
				logger.error(null,e);
			}
		}
	}
	public void destroy() {
		this.isDestroy = true;
	}
}
