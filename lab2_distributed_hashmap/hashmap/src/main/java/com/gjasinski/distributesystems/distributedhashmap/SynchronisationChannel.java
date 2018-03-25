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
import org.jgroups.stack.ProtocolStack;

import static com.gjasinski.distributesystems.distributedhashmap.MapCommunication.*;

class SynchronisationChannel {
	private final JChannel channel;
	private final String channelName;
	private Map map;

	SynchronisationChannel(String channelName) throws Exception {
		this.channelName = channelName;
		channel = new JChannel(getProtocolStack());
	}

	private ProtocolStack getProtocolStack() throws Exception {
		System.setProperty("java.net.preferIPv4Stack","true");
		ProtocolStack stack = new ProtocolStack()
				.addProtocol(new UDP())
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
				.addProtocol(new FLUSH());
		System.out.println(stack);
		stack.init();
		return stack;
	}

	void startSynchronization(Map map) throws Exception {
		this.map = map;
		channel.connect(channelName);
		channel.getState();
		channel.setReceiver(new CustomReceiverAdapter(map));

	}

	public void send(MapOperation operation) throws Exception {
		channel.send(new Message(null, operation));
	}
}
