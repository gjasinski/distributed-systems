package com.gjasinski.distributesystems.distributedhashmap;

import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

import java.io.InputStream;
import java.io.OutputStream;

public class CustomReceiverAdapter extends ReceiverAdapter {
	private final Map map;

	public CustomReceiverAdapter(Map map) {
		this.map = map;
	}

	@Override
	public void viewAccepted(View view) {
		super.viewAccepted(view);
		System.out.println(view.toString());
	}

	public void receive(Message msg) {
		System.out.println("received msg from "
				+ msg.getSrc() + ": "
				+ msg.getObject());
	}

	@Override
	public void getState(OutputStream output) throws Exception{
		map.getState(output);
	}

	@Override
	public void setState(InputStream input) throws Exception{
		map.setState(input);
	}
}
