package com.auctionmachine.core.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class AuctionEntry {
	
	private String auctionEntryId;
	private String auctionEntryName;
	private Integer currentPrice;
	private String currentHolderUserId;
	private List<PreBid> preBidList = new ArrayList<>();
	private List<LiveBid> bidLogList = new ArrayList<>();
	private AuctionLane auctionLane;
	
	public AuctionEntry(){
	}
	public AuctionEntry(AuctionLane auctionLane){
		this.auctionLane = auctionLane;
	}
	
	public void setCurrentPrice(Integer currentPrice) {
		this.currentPrice = currentPrice;
		this.auctionLane.getAuctionLaneMonitor().setCurrentPrice(currentPrice);
	}
	
	//出品ID
	//出品開催回
	//出品会員ID
	//現在価格
	//出品詳細
	//流れタイマー
	//ランプ
	//売切タイマー
	//権利取得中フラグ
	//自社出品フラグ
	//オークションステータス
	//ビッドアップステータス
	//接近音1
	//接近音2
	//接近音3
	//テストモードフラグ
	//訂正フラグ
	//次出品ID
	//次々出品ID
	//スタート価格
	//希望価格
	//最終応札会員ID
	//最終応札会員名
	//最終応札価格
	//オークション実施時刻
	//オークション経過時間
	//接続人数
	//応札人数
	//応札マトリックス
	//第1入札会員ID
	//第1入札価格
	//第2入札会員ID
	//第2入札価格
	//売切タイマー間隔
}
