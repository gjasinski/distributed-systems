package com.gjasinski.distributesystems.distributedhashmap;

import com.gjasinski.distributesystems.distributedhashmap.MapCommunication.BooleanResult;
import com.gjasinski.distributesystems.distributedhashmap.MapCommunication.MapOperation;
import com.gjasinski.distributesystems.distributedhashmap.MapCommunication.StringResult;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.Charset;
import java.util.Arrays;

public class CommandChannel {
	private static final int PACKET_SIZE = 1024;
	private final DatagramSocket socket;
	private final Map map;

	public CommandChannel(DatagramSocket socket, Map map) {
		this.socket = socket;
		this.map = map;
	}

	void run() throws IOException {
		while (true) {
			byte[] input = new byte[PACKET_SIZE];
			DatagramPacket datagramPacket = new DatagramPacket(input, PACKET_SIZE);
			socket.receive(datagramPacket);
			int receivedBytes = datagramPacket.getLength();
			byte[] trimmedInput = Arrays.copyOfRange(input, 0, receivedBytes);
			MapOperation operation = MapOperation.parseFrom(trimmedInput);
			System.out.println(operation);
			System.out.println(new String(operation.getKey().getBytes(), Charset.forName("UTF-8")));
			byte[] result = makeOperation(operation);
			datagramPacket.setData(result, 0, result.length);
			socket.send(datagramPacket);
		}
	}

	byte[] makeOperation(MapOperation operation) {
		switch (operation.getType()) {
			case CONTAINS_KEY:
				return createBooleanResult(map.containsKey(operation.getKey()));
			case GET:
				return createStringResult(map.get(operation.getKey()));
			case PUT:
				return createStringResult(map.put(operation.getKey(), operation.getValue()));
			case REMOVE:
				return createStringResult(map.remove(operation.getKey()));
			default:
				throw new IllegalArgumentException("Not implemented operation type" + operation.getType());
		}
	}

	private byte[] createBooleanResult(boolean result) {
		return BooleanResult.newBuilder()
				.setResult(result)
				.build()
				.toByteArray();
	}


	private byte[] createStringResult(String result) {
		return StringResult.newBuilder()
				.setTesult(result)
				.build()
				.toByteArray();
	}

}
