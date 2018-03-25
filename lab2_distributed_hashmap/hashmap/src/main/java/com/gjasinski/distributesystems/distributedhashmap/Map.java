package com.gjasinski.distributesystems.distributedhashmap;

import org.jgroups.util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ConcurrentHashMap;

public class Map implements SimpleStringMap {
	private final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

	@Override
	public boolean containsKey(String key) {
		return map.containsKey(key);
	}

	@Override
	public String get(String key) {
		return map.get(key);
	}

	@Override
	public String put(String key, String value) {
		return map.put(key, value);
	}

	@Override
	public String remove(String key) {
		return map.remove(key);
	}

	public void getState(OutputStream output) throws Exception {
		synchronized (map) {
			Util.objectToStream(map, new DataOutputStream(output));
		}
	}

	public void setState(InputStream input) throws Exception {
		ConcurrentHashMap<String, String> receivedMap = Util.objectFromStream(new DataInputStream(input));
		synchronized (this.map) {
			map.clear();
			map.putAll(receivedMap);
		}
		System.out.println(receivedMap.size() + " data in map:");
		for (java.util.Map.Entry<String, String> val : receivedMap.entrySet()) {
			System.out.println(val.getKey() + " " + val.getValue());
		}
	}
}
