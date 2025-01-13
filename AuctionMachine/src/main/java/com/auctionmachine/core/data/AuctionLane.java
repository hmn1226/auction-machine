package com.auctionmachine.core.data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import com.auctionmachine.lib.AuctionLaneStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "auctionLaneId")
public class AuctionLane {
	
	private AuctionRoom auctionRoom;
	private Integer auctionLaneId;
	private AuctionLaneStatus auctionLaneStatus = AuctionLaneStatus.STOP;
	private Queue<LiveBid> liveBidQueue = new LinkedList<>();
	private final Map<String,AuctionEntry> auctionEntryMap = new LinkedHashMap<>();
	private int auctionEntryKeySetPointer=0;
	private int bidInterval = 1000;
	private int liveBidCount = 0;
	
	private AuctionLaneMonitor auctionLaneMonitor;
	
	private String topPreBidUserId=null;
	private Integer topPreBidPrice=null;
	private String secondPreBidUserId=null;
	private Integer secondPreBidPrice=null;
	private Integer preBidReflectTimer=null;
	private String preBidReflectUserId=null;
	//---応札マトリックスは10×5=50個
	
	public AuctionLane(AuctionRoom auctionRoom,Integer auctionLaneId){
		this.auctionRoom = auctionRoom;
		this.auctionLaneId = auctionLaneId;
	}
	public AuctionEntry getCurrentAuctionEntry() {
		if(auctionEntryKeySetPointer<this.auctionEntryMap.keySet().size() ) {
			List<String> keyList = new ArrayList<>(this.auctionEntryMap.keySet());
			return this.auctionEntryMap.get(keyList.get(auctionEntryKeySetPointer));
		}else{
			return null;
		}
	}
	public void incrementPointer() {
		if(this.auctionEntryKeySetPointer < this.auctionEntryMap.keySet().size()-1) {
			this.auctionEntryKeySetPointer++;
			displayMonitor();
		}
	}
	public void decrementPointer() {
		if(this.auctionEntryKeySetPointer > 0) {
			this.auctionEntryKeySetPointer--;
			displayMonitor();
		}
	}
	
	public void setAuctionLaneStatus(AuctionLaneStatus auctionLaneStatus) {
		this.auctionLaneStatus = auctionLaneStatus;
		this.auctionLaneMonitor.setAuctionLaneStatus(auctionLaneStatus);
	}
	public void setCurrentPrice(Integer currentPrice) {
		this.getCurrentAuctionEntry().setCurrentPrice(currentPrice);
		this.auctionLaneMonitor.setCurrentPrice(currentPrice);
	}
	public void setCurrentHolderUserId(String currentHolderUserId) {
		this.getCurrentAuctionEntry().setCurrentHolderUserId(currentHolderUserId);
		this.auctionLaneMonitor.setCurrentHolderUserId(currentHolderUserId);
	}
	public void displayMonitor() {
		AuctionEntry ae = this.getCurrentAuctionEntry();
		this.auctionLaneMonitor.setAuctionRoomId(ae.getAuctionLane().getAuctionRoom().getAuctionRoomId());
		this.auctionLaneMonitor.setAuctionLaneId(ae.getAuctionLane().getAuctionLaneId());
		this.auctionLaneMonitor.setAuctionEntryId(ae.getAuctionEntryId());
		this.auctionLaneMonitor.setAuctionEntryName(ae.getAuctionEntryName());
		this.auctionLaneMonitor.setCurrentPrice(ae.getCurrentPrice());
		this.auctionLaneMonitor.setCurrentHolderUserId(ae.getCurrentHolderUserId());
		this.auctionLaneMonitor.setAuctionLaneStatus(auctionLaneStatus);
	}
	public void resolvePreBids() {
		AuctionEntry auctionEntry = this.getCurrentAuctionEntry();
		if(this.preBidReflectTimer!=null) {//事前入札反映タイマー作動中
			this.preBidReflectTimer--;
			if(this.preBidReflectTimer==0) {
				LiveBid liveBid = new LiveBid();
				liveBid.setAuctionEntryId(auctionEntry.getAuctionEntryId());
				liveBid.setBidUserId(preBidReflectUserId);
				liveBid.setBidTime(Instant.now());
				this.getLiveBidQueue().add(liveBid);
			}
		}else{
			//事前入札金額降順にソート
			auctionEntry.getPreBidList().sort(Comparator.comparingInt(PreBid::getBidPrice).reversed());
			
			int i=0;
			for(PreBid preBid : auctionEntry.getPreBidList()) {
				switch(i) {
				case 0://1位事前入札
					topPreBidUserId = preBid.getBidUserId();
					topPreBidPrice = preBid.getBidPrice();
					break;
				case 1://2位事前入札
					secondPreBidUserId = preBid.getBidUserId();
					secondPreBidPrice = preBid.getBidPrice();
					break;
				}
				i++;
			}
			if(topPreBidUserId!=null && topPreBidPrice!=null) {
				if(topPreBidPrice >= auctionEntry.getCurrentPrice()+getBidInterval()) {
					//PreBidのユーザーIDが権利者ではない場合、もしくは2位入札者が居て2位入札金額が現在価格より高い場合
					if(!topPreBidUserId.equals(auctionEntry.getCurrentHolderUserId()) ||
							(secondPreBidUserId!=null && secondPreBidPrice>auctionEntry.getCurrentPrice()) ) {
						Random random = new Random();
				        this.preBidReflectTimer = random.nextInt(9) + 1; //---1〜9回の中でランダムに反映させる
				        this.preBidReflectUserId = topPreBidUserId;
					}
				}
			}
		}
	}
	
	public void resolveLiveBids() {
		this.liveBidCount = liveBidQueue.size();
		if(this.liveBidCount>0) {
			AuctionEntry auctionEntry = this.getCurrentAuctionEntry();
			LiveBid liveBid=null;
			int i=0;
			while( (liveBid=this.liveBidQueue.poll() )!=null ) {
				if(i==0) {//---最速の入札者が権利者
					this.setCurrentPrice(auctionEntry.getCurrentPrice()+this.getBidInterval());
					this.setCurrentHolderUserId(liveBid.getBidUserId());
				}
				auctionEntry.getBidLogList().add(liveBid);//---キューから応札ログへ蓄積
				i++;
			}
			//現在価格の変更があった為PreBidの反映予約はクリアする
			this.preBidReflectTimer=null;
			this.preBidReflectUserId=null;
			this.topPreBidUserId=null;
			this.topPreBidPrice=null;
			this.secondPreBidUserId=null;
			this.secondPreBidPrice=null;
		}
	}
	//フィールド初期化()
	//セット()
	//入札()
	//セットスタート価格()
	//セット希望価格()
	//スタート()
	//売切()
	//保留()
	//一時停止()
	//落札()
	//流れ()
	//リスタート()
	//訂正()
	//テストモード()
	
	

}
