package com.gjasinski.distributedsystems.distributedhashmapclient;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class Client {
	private static final InetSocketAddress server = new InetSocketAddress("localhost", 9999);

	public static void main(String[] args) throws Exception {
		Channel channel = new Channel(new DatagramSocket(), server);
		Map map = new Map(channel);
		System.out.println(map.put("ala ma kota", "kota ma ale"));
		System.out.println(map.containsKey("witcher"));
		System.out.println(map.remove("czarne słońce"));
		System.out.println(map.get("jaskółka"));
	}
}
