package com.auctionmachine.core.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auctionmachine.core.data.AuctionEntry;
import com.auctionmachine.core.data.AuctionLane;
import com.auctionmachine.core.data.AuctionRoom;
import com.auctionmachine.util.BeanUtil;
import com.auctionmachine.web.socket.controller.ChatMessage;
import com.auctionmachine.web.socket.service.MessagingService;

public class AuctionLaneThread extends Thread{
	
	private Logger logger = LoggerFactory.getLogger(super.getClass());
	
	private AuctionLane auctionLane;
	
	boolean isDestroy=false;
	
	public AuctionLaneThread(AuctionLane auctionLane) {
		this.auctionLane = auctionLane;
	}
	

	@Override
	public void run() {
		this.auctionItemLoop();
	}
	
	private void auctionItemLoop(){
		AuctionEntry auctionItemToCompare = null;
		while(!isDestroy) {
			try {
				//---
				AuctionRoom auctionRoom = this.auctionLane.getAuctionRoom();
				AuctionLane auctionLane = this.auctionLane;
				AuctionEntry auctionEntry = this.auctionLane.getCurrentAuctionEntry();
				//---
				
				if(auctionEntry==null) continue;
				auctionItemToCompare = BeanUtil.deepCopy(AuctionEntry.class, auctionEntry);
				/*
				logger.info(
						this.auctionLane.getAuctionRoomId()+"==="+
						this.auctionLane.getAuctionLaneId()+"==="+
				"Auction Lane Thread Loop!");
				*/
				super.sleep(1000);
				
				//---前回ループ時のAuctionItemと内容の変更があった時
				if(!BeanUtil.equal(auctionEntry,auctionItemToCompare)) {
					logger.info("変わった");
				}
				
				MessagingService ms = MessagingService.getInstance();
				ChatMessage cm = new ChatMessage();
				cm.setContent(
						auctionLane.getAuctionLaneStatus() + 
						auctionEntry.getAuctionEntryName()+"---"+
						auctionEntry.getCurrentPrice()
				);
				ms.sendMessage("/topic/messages/room1" ,cm);
				
				//logger.info(auctionItem.getItemName()+"---"+auctionItem.getCurrentPrice());

			}catch(Exception e) {
				logger.error(null,e);
			}
		}
	}
	
	public void destroy() {
		this.isDestroy = true;
	}
}
