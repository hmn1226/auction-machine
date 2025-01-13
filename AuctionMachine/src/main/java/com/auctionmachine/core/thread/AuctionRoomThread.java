package com.auctionmachine.core.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auctionmachine.core.data.AuctionLaneMonitor;
import com.auctionmachine.core.data.AuctionRoom;
import com.auctionmachine.web.socket.model.MessagingModel;
import com.auctionmachine.web.socket.service.MessagingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuctionRoomThread extends Thread{
	
	private Logger logger = LoggerFactory.getLogger(super.getClass());
	
	private AuctionRoom auctionRoom;
	private boolean isDestroy=false;
	
	Integer loopInterval = 100;

	public AuctionRoomThread(AuctionRoom auctionRoom) {
		this.auctionRoom = auctionRoom;
	}
	
	@Override
	public void run() {
		this.auctionRoomLoop();
	}
	
	private void auctionRoomLoop(){
		while(!isDestroy) {
			try {
				super.sleep(this.loopInterval);
				this.send();
			}catch(Exception e) {
				logger.error(null,e);
			}
		}
	}
	
	private void send() throws JsonProcessingException {
		MessagingService ms = MessagingService.getInstance();
		for(AuctionLaneMonitor alm :  this.auctionRoom.getAuctionRoomMonitor().getAuctionLaneMonitors() ) {
			alm.setHoge(alm.getHoge()+1);
		}
		MessagingModel model = new MessagingModel();
		ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(this.auctionRoom.getAuctionRoomMonitor());
        model.setContent(jsonString);
		ms.sendMessage("/topic/messages/room1" ,model);
	}
	
	public void destroy() {
		this.isDestroy = true;
	}
}
