package com.gjasinski.distributedsystems.distributedhashmapclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Arrays;


class Channel {
	private final static int DATAGRAM_SIZE = 1024;
	private final DatagramSocket datagramSocket;
	private final InetSocketAddress server;

	Channel(DatagramSocket datagramSocket, InetSocketAddress server) {
		this.datagramSocket = datagramSocket;
		this.server = server;
	}

	void makeOperation(MapCommunication.MapOperation operation) throws IOException {
		datagramSocket.send(new DatagramPacket(operation.toByteArray(), operation.toByteArray().length, server));
	}

	MapCommunication.BooleanResult receiveBooleanResult() throws IOException {
		return MapCommunication.BooleanResult.parseFrom(receivePacket());
	}

	MapCommunication.StringResult receiveStringResult() throws IOException {
		return MapCommunication.StringResult.parseFrom(receivePacket());
	}

	private byte[] receivePacket() throws IOException {
		byte[] input = new byte[DATAGRAM_SIZE];
		DatagramPacket packet = new DatagramPacket(input, input.length);
		datagramSocket.receive(packet);
		return Arrays.copyOfRange(input, 0, packet.getLength());
	}

}
