package com.gjasinski.distributesystems.distributedhashmap;

import org.jgroups.JChannel;
import org.jgroups.Message;
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
import org.jgroups.protocols.pbcast.STATE_TRANSFER;
import org.jgroups.stack.Protocol;
import org.jgroups.stack.ProtocolStack;

import java.net.InetAddress;

import static com.gjasinski.distributesystems.distributedhashmap.MapCommunication.*;

class SynchronisationChannel {
	private final String channelName;
	private final JChannel channel;
	private Operation operation;

	SynchronisationChannel(String channelName) throws Exception {
		this.channelName = channelName;
		this.channel = new JChannel(false);
	}

	private ProtocolStack getProtocols() throws Exception {
	//	System.setProperty("java.net.preferIPv4Stack","true");

		ProtocolStack stack = new ProtocolStack();

		Protocol udpProtocol = new UDP();
		udpProtocol.setValue("mcast_group_addr", InetAddress.getByName("230.0.0.10"));

		stack.addProtocol(udpProtocol)
				.addProtocol(new PING())
				.addProtocol(new MERGE3())
				.addProtocol(new FD_SOCK())
				.addProtocol(new FD_ALL().setValue("timeout", 12000).setValue("interval", 3000))
				.addProtocol(new VERIFY_SUSPECT())
				.addProtocol(new BARRIER())
				.addProtocol(new NAKACK2())
				.addProtocol(new UNICAST3())
				.addProtocol(new STABLE())
				.addProtocol(new GMS())
				.addProtocol(new UFC())
				.addProtocol(new MFC())
				.addProtocol(new FRAG2())
				.addProtocol(new SEQUENCER())
				.addProtocol(new STATE_TRANSFER())
				.addProtocol(new FLUSH());
		return stack;
	}

	void startSynchronization(Operation operation) throws Exception {
		try {
			this.operation = operation;
			channel.setReceiver(new CustomReceiverAdapter(operation, channel));
			ProtocolStack protocols = getProtocols();
			channel.setProtocolStack(protocols);
			protocols.init();
			channel.connect(channelName);
			channel.getState(null, 1000);
		}catch (Exception ex){
			System.out.println(ex.toString());
		}

	}

	public void send(MapOperation operation) throws Exception {
		try {
			channel.send(new Message(null, operation));
		}catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
}
