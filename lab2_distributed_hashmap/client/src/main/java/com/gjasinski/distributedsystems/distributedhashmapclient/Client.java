package com.gjasinski.distributedsystems.distributedhashmapclient;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws Exception {
		String port = readLine();
		InetSocketAddress server = new InetSocketAddress("localhost", Integer.valueOf(port));
		Channel channel = new Channel(new DatagramSocket(), server);
		Map map = new Map(channel);
		while(true){
			String key, value;
			String line = readLine();
			switch (line){
				case "put":
					key = readLine();
					value = readLine();
					System.out.println(map.put(key, value));
					break;
				case "get":
					key = readLine();
					System.out.println(map.get(key));
					break;
				case "remove":
					key = readLine();
					System.out.println(map.remove(key));
					break;
				case "contains":
					key = readLine();
					System.out.println(map.containsKey(key));
					break;
				default:
					System.out.println("Type: put, get, remove or contains");
			}
		}
	}

	private static String readLine() {
		Scanner s = new Scanner(System.in);
		return s.nextLine();
	}
}
