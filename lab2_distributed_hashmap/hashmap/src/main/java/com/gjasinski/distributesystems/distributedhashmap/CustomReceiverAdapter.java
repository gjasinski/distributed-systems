package com.gjasinski.distributesystems.distributedhashmap;

import com.gjasinski.distributesystems.distributedhashmap.MapCommunication.MapOperation;
import org.jgroups.Address;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

import java.io.InputStream;
import java.io.OutputStream;

public class CustomReceiverAdapter extends ReceiverAdapter {
	private final Operation operation;
	private final Address myAddress;

	CustomReceiverAdapter(Operation operation, Address myAddress) {
		this.operation = operation;
		this.myAddress = myAddress;
	}

	@Override
	public void viewAccepted(View view) {
		super.viewAccepted(view);
		System.out.println("viewAccepted" + view.toString());
	}

	public void receive(Message msg) {
		if(!ignoreOperation(msg)) {
			MapOperation mapOperation = msg.getObject();
			byte[] result = operation.makeOperation(mapOperation);
			System.out.println("received msg from "
					+ msg.getSrc() + ": "
					+ mapOperation + " result: " + result.length);
		}
	}

	private boolean ignoreOperation(Message msg) {
		return msg.getSrc().equals(myAddress);
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
