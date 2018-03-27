package com.gjasinski.distributesystems.distributedhashmap;

import com.gjasinski.distributesystems.distributedhashmap.MapCommunication.MapOperation;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.Charset;
import java.util.Arrays;

public class CommandChannel {
	private static final int PACKET_SIZE = 1024;
	private final DatagramSocket socket;
	private final Operation operation;

	CommandChannel(DatagramSocket socket, Operation operation) {
		this.socket = socket;
		this.operation = operation;
	}

	void run() throws Exception {
		while (true) {
			byte[] input = new byte[PACKET_SIZE];
			DatagramPacket datagramPacket = new DatagramPacket(input, PACKET_SIZE);
			socket.receive(datagramPacket);
			int receivedBytes = datagramPacket.getLength();
			byte[] trimmedInput = Arrays.copyOfRange(input, 0, receivedBytes);
			MapOperation mapOperation = MapOperation.parseFrom(trimmedInput);
			System.out.println(mapOperation);
			System.out.println(new String(mapOperation.getKey().getBytes(), Charset.forName("UTF-8")));
			operation.propagateChanges(mapOperation);
			byte[] result = operation.makeOperation(mapOperation);
			datagramPacket.setData(result, 0, result.length);
			socket.send(datagramPacket);
		}
	}

}
