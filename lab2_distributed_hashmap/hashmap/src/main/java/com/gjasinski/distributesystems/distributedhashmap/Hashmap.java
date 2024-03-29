package com.gjasinski.distributesystems.distributedhashmap;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class Hashmap {
	private static final int PORT = 9999;

	public static void main(String[] args) throws Exception {
		System.setProperty("java.net.preferIPv4Stack", "true");
		DatagramSocket socket;
		try {
			socket = new DatagramSocket(PORT);
		} catch (Exception ex) {
			socket = new DatagramSocket();
		}
		SynchronisationChannel synchronisationChannel = new SynchronisationChannel("map_synchronisation");

		StringMap map = new VerboseMap(new Map());
		Operation operation = new Operation(map, synchronisationChannel);
		synchronisationChannel.startSynchronization(operation);
		new CommandChannel(socket, operation).run();
	}
}
