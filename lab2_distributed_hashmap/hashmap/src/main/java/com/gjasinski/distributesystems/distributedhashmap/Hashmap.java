package com.gjasinski.distributesystems.distributedhashmap;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class Hashmap {
	private static final InetSocketAddress server = new InetSocketAddress("230.0.0.10", 9999);
	private static final int PORT = 9999;
	public static void main(String[] args) throws IOException {
		DatagramSocket socket = new DatagramSocket(PORT);
		Map map = new Map();
		new CommandChannel(socket, map).run();
	}
}
