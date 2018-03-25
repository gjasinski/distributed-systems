package com.gjasinski.distributedsystems.distributedhashmapclient;

import org.jgroups.JChannel;
import org.jgroups.protocols.BARRIER;
import org.jgroups.protocols.FD_ALL;
import org.jgroups.protocols.FD_SOCK;
import org.jgroups.protocols.FRAG2;
import org.jgroups.protocols.MERGE3;
import org.jgroups.protocols.MFC;
import org.jgroups.protocols.PING;
import org.jgroups.protocols.SEQUENCER;
import org.jgroups.protocols.UDP;
import org.jgroups.protocols.UFC;
import org.jgroups.protocols.UNICAST3;
import org.jgroups.protocols.VERIFY_SUSPECT;
import org.jgroups.protocols.pbcast.FLUSH;
import org.jgroups.protocols.pbcast.GMS;
import org.jgroups.protocols.pbcast.NAKACK2;
import org.jgroups.protocols.pbcast.STABLE;
import org.jgroups.stack.ProtocolStack;

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
//
//	//
//	void join() throws Exception {
//		ProtocolStack stack = new ProtocolStack();
//		stack.addProtocol(new UDP())
//				.addProtocol(new PING())
//				.addProtocol(new MERGE3())
//				.addProtocol(new FD_SOCK())
//				.addProtocol(new FD_ALL().setValue("timeout", 12000).setValue("interval", 3000))
//				.addProtocol(new VERIFY_SUSPECT())
//				.addProtocol(new BARRIER())
//				.addProtocol(new NAKACK2())
//				.addProtocol(new UNICAST3())
//				.addProtocol(new STABLE())
//				.addProtocol(new GMS())
//				.addProtocol(new UFC())
//				.addProtocol(new MFC())
//				.addProtocol(new FRAG2())
//				.addProtocol(new SEQUENCER())
//				.addProtocol(new FLUSH());
//		stack.init();
//		new JChannel(stack);
//	}

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
