package com.gjasinski.distributesystems.distributedhashmap;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.MergeView;
import org.jgroups.View;

import java.util.List;

public class ViewHandler extends Thread {
	private final MergeView view;
	private final JChannel channel;

	ViewHandler(MergeView view, JChannel channel) {
		this.view = view;
		this.channel = channel;
	}

	@Override
	public void run() {
		List<View> subgroups = view.getSubgroups();
		View selectedView = subgroups.get(0);
		Address address = channel.getAddress();
		if (!selectedView.getMembers().contains(address)) {
			System.out.println("Not member of the new primary partition ("
					+ selectedView + "), will re-acquire the state");
			try {
				channel.getState(null, 30000);
			} catch (Exception ex) {
			}
		} else {
			System.out.println("Member of the new primary partition ("
					+ selectedView + "), will do nothing");
		}

	}
}
