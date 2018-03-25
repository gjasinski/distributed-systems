package com.gjasinski.distributesystems.distributedhashmap;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class Hashmap {
	private static final InetSocketAddress server = new InetSocketAddress("230.0.0.10", 9999);
	private static final int PORT = 9999;

	public static void main(String[] args) throws Exception {
		System.setProperty("java.net.preferIPv4Stack", "true");
		SynchronisationChannel synchronisationChannel = new SynchronisationChannel("map_synchronisation");
		DatagramSocket socket;
		try {
			socket = new DatagramSocket(PORT);
		} catch (Exception ex) {
			socket = new DatagramSocket();
		}
		Map map = new Map();
		synchronisationChannel.startSynchronization(map);
		new CommandChannel(socket, map, synchronisationChannel).run();
	}
}
