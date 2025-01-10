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
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
public class AuctionLane {
	
	//====★★★★★★★★★★★★★★★ WEB SOCKET配信用 簡易Lane Beanをコンストラクタで準備
	// AuctionLaneと上記Beanを両方更新する場合はsetterに両方更新するように記述する
	@JsonBackReference
	AuctionRoom auctionRoom;
	Integer auctionLaneId;
	AuctionLaneStatus auctionLaneStatus = AuctionLaneStatus.STOP;
	Queue<LiveBid> liveBidQueue = new LinkedList<>();
	private final Map<String,AuctionEntry> auctionEntryMap = new LinkedHashMap<>();
	int auctionEntryKeySetPointer=0;
	int bidInterval = 1000;
	int liveBidCount = 0;
	
	AuctionLaneMonitor auctionLaneMonitor;
	
	String topPreBidUserId=null;
	Integer topPreBidPrice=null;
	String secondPreBidUserId=null;
	Integer secondPreBidPrice=null;
	Integer preBidReflectTimer=null;
	String preBidReflectUserId=null;
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
		}
	}
	public void decrementPointer() {
		if(this.auctionEntryKeySetPointer > 0) {
			this.auctionEntryKeySetPointer--;
		}
	}
	public void displayMonitor() {
		AuctionEntry ae = this.getCurrentAuctionEntry();
		this.auctionLaneMonitor.setAuctionEntryId(ae.getAuctionEntryId());
		this.auctionLaneMonitor.setAuctionEntryName(ae.getAuctionEntryName());
		this.auctionLaneMonitor.setCurrentPrice(ae.getCurrentPrice());
	}
	public void resolvePreBids() {
		AuctionEntry auctionEntry = this.getCurrentAuctionEntry();
		if(this.preBidReflectTimer!=null) {//事前入札反映タイマー作動中
			this.preBidReflectTimer--;
			if(this.preBidReflectTimer==0) {
				LiveBid liveBid = new LiveBid();
				liveBid.setAuctionEntryId(auctionEntry.getAuctionEntryId());
				liveBid.setUserId(preBidReflectUserId);
				liveBid.setBidTime(Instant.now());
				this.getLiveBidQueue().add(liveBid);
			}
		}else{
			//事前入札金額降順にソート
			auctionEntry.getPreBidList().sort(Comparator.comparingInt(PreBid::getBidPrice).reversed());
			
			int i=0;
			for(PreBid preBid : auctionEntry.getPreBidList()) {
				System.out.println(preBid);
				switch(i) {
				case 0://1位事前入札
					topPreBidUserId = preBid.getUserId();
					topPreBidPrice = preBid.getBidPrice();
					break;
				case 1://2位事前入札
					secondPreBidUserId = preBid.getUserId();
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
					auctionEntry.setCurrentPrice(auctionEntry.getCurrentPrice()+this.getBidInterval());
					auctionEntry.setCurrentHolderUserId(liveBid.getUserId());
				}
				auctionEntry.getBidLogList().add(liveBid);//---キューから応札ログへ蓄積
				System.out.println(i+"--"+liveBid);
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
