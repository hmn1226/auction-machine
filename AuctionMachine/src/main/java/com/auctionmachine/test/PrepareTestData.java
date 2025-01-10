package com.auctionmachine.test;

import java.time.Instant;

import com.auctionmachine.core.DataStore;
import com.auctionmachine.core.data.AuctionEntry;
import com.auctionmachine.core.data.AuctionLane;
import com.auctionmachine.core.data.AuctionRoom;
import com.auctionmachine.core.data.PreBid;

public class PrepareTestData {
	
	public void run() {
		DataStore dataStore = DataStore.getInstance();
		{//===1ルーム目
			String auctionRoomId = "abc-defg-hij";
			AuctionRoom auctionRoom = dataStore.addAuctionRoom(auctionRoomId);
			AuctionLane auctionLane = auctionRoom.addAuctionLane();//---1レーン追加
			{
				AuctionEntry auctionEntry = new AuctionEntry(auctionLane);
				auctionEntry.setAuctionEntryId("1");
				auctionEntry.setAuctionEntryName("商品A");
				auctionEntry.setCurrentPrice(10000);
				auctionLane.getAuctionEntryMap().put("1",auctionEntry);
				
				{
					PreBid pb = new PreBid();
					pb.setAuctionEntryId("1");
					pb.setBidPrice(12000);
					pb.setBidTime(Instant.now());
					pb.setUserId("ABC12000");
					auctionEntry.getPreBidList().add(pb);
				}
				{
					PreBid pb = new PreBid();
					pb.setAuctionEntryId("1");
					pb.setBidPrice(24000);
					pb.setBidTime(Instant.now());
					pb.setUserId("ABC24000");
					auctionEntry.getPreBidList().add(pb);
				}
				{
					PreBid pb = new PreBid();
					pb.setAuctionEntryId("1");
					pb.setBidPrice(4000);
					pb.setBidTime(Instant.now());
					pb.setUserId("ABC4000");
					auctionEntry.getPreBidList().add(pb);
				}
				{
					PreBid pb = new PreBid();
					pb.setAuctionEntryId("1");
					pb.setBidPrice(16000);
					pb.setBidTime(Instant.now());
					pb.setUserId("ABC16000");
					auctionEntry.getPreBidList().add(pb);
				}
				
			}
			{
				AuctionEntry auctionEntry = new AuctionEntry(auctionLane);
				auctionEntry.setAuctionEntryId("2");
				auctionEntry.setAuctionEntryName("商品B");
				auctionEntry.setCurrentPrice(20000);
				auctionLane.getAuctionEntryMap().put("2",auctionEntry);
			}
			{
				AuctionEntry auctionEntry = new AuctionEntry(auctionLane);
				auctionEntry.setAuctionEntryId("3");
				auctionEntry.setAuctionEntryName("商品C");
				auctionEntry.setCurrentPrice(30000);
				auctionLane.getAuctionEntryMap().put("3",auctionEntry);
			}
		}
		
		//===================================================
		
		
		
	}
}
