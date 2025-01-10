package com.auctionmachine;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.auctionmachine.test.PrepareTestData;

@Component
public class InitService {
	@PostConstruct
	public void init() {
		System.out.println("アプリケーション起動時に実行されます");
		{
			System.out.println("redisからオブジェクトを復元する");
			System.out.println("---------------------------");
			System.out.println("AuctionRoomのオブジェクトからスレッド立ち上げ");
			System.out.println("---------------------------");
		}
		new PrepareTestData().run();
	}
}