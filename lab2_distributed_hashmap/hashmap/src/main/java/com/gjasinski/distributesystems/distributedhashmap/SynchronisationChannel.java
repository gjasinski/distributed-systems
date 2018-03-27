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
import org.jgroups.protocols.UDP;
import org.jgroups.protocols.UFC;
import org.jgroups.protocols.UNICAST3;
import org.jgroups.protocols.VERIFY_SUSPECT;
import org.jgroups.protocols.pbcast.GMS;
import org.jgroups.protocols.pbcast.NAKACK2;
import org.jgroups.protocols.pbcast.STABLE;
import org.jgroups.stack.Protocol;

import java.net.InetAddress;

import static com.gjasinski.distributesystems.distributedhashmap.MapCommunication.*;

class SynchronisationChannel {
	private final JChannel channel;
	private final String channelName;
	private Operation operation;

	SynchronisationChannel(String channelName) throws Exception {
		this.channelName = channelName;
		channel = new JChannel(getProtocols());
	}

	private Protocol[] getProtocols() throws Exception {
		return new Protocol[]{
				new UDP().setValue("mcast_group_addr",InetAddress.getByName("230.0.0.1")),
				new PING(),
				new MERGE3(),
				new FD_SOCK(),
				new FD_ALL(),
				new VERIFY_SUSPECT(),
				new BARRIER(),
				new NAKACK2(),
				new UNICAST3(),
				new STABLE(),
				new GMS(),
				new UFC(),
				new MFC(),
				new FRAG2()};
	}

	void startSynchronization(Operation operation) throws Exception {
		this.operation = operation;
		channel.connect(channelName);
		channel.getState();
		channel.setReceiver(new CustomReceiverAdapter(operation, channel.getAddress()));
		System.out.println(channel.getState());

	}

	public void send(MapOperation operation) throws Exception {
		channel.send(new Message(null, operation));
	}
}
