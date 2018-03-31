package com.gjasinski.distributesystems.distributedhashmap;

import com.gjasinski.distributesystems.distributedhashmap.MapCommunication.MapOperation;
import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.MergeView;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

import java.io.InputStream;
import java.io.OutputStream;

public class CustomReceiverAdapter extends ReceiverAdapter {
	private final Operation operation;
	private final JChannel channel;

	CustomReceiverAdapter(Operation operation, JChannel jChannel) {
		this.operation = operation;
		this.channel = jChannel;
	}

	@Override
	public void viewAccepted(View view) {
		System.out.println("HAVE VIEW: " + view);
		if(view instanceof MergeView) {
			System.out.println("RUN MERGING");
			new ViewHandler((MergeView)view, channel).start();
		}
	}

	public void receive(Message msg) {
		if(!ignoreOperation(msg)) {
			MapOperation mapOperation = (MapOperation)msg.getObject();
			System.out.println("SYNCHRONIZATION FROM " + msg.getSrc() + ": ");
			operation.makeOperation(mapOperation);
		}
	}

	private boolean ignoreOperation(Message msg) {
		return msg.getSrc().equals(channel.getAddress());
	}

	@Override
	public void getState(OutputStream output) throws Exception {
		operation.getState(output);
	}

	@Override
	public void setState(InputStream input) throws Exception {
		operation.setState(input);
	}
}
