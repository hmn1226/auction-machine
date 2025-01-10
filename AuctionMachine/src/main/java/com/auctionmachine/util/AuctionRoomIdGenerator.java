package com.auctionmachine.util;
import java.util.Random;

public class AuctionRoomIdGenerator {

	// 小文字のアルファベット文字列
	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

	// ランダム文字列を生成する関数
	public static String generateAuctionRoomId() {
		Random random = new Random();

		// 3文字 + "-" + 4文字 + "-" + 3文字のコードを生成
		String part1 = getRandomString(random, 3);
		String part2 = getRandomString(random, 4);
		String part3 = getRandomString(random, 3);

		return part1 + "-" + part2 + "-" + part3;
	}

	// 指定された文字数のランダムな小文字アルファベット文字列を生成するヘルパーメソッド
	private static String getRandomString(Random random, int length) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			char randomChar = ALPHABET.charAt(random.nextInt(ALPHABET.length()));
			sb.append(randomChar);
		}
		return sb.toString();
	}

	// メインメソッドで動作確認
	public static void main(String[] args) {
		// 5つのランダムコードを生成して表示
		for (int i = 0; i < 5; i++) {
			System.out.println(generateAuctionRoomId());
		}
	}
}