package com.gjasinski.distributedsystems.distributedhashmapclient;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class Client {
	private static final InetSocketAddress server = new InetSocketAddress("localhost", 9999);

	public static void main(String[] args) throws Exception {
		Channel channel = new Channel(new DatagramSocket(), server);
		channel.join();
		Map map = new Map(channel);
		map.put("ala ma kota", "kota ma ale");
		map.containsKey("witcher");
		map.remove("czarne słońce");
		map.get("jaskółka");
	}
}
