package com.gjasinski.distributesystems.distributedhashmap;

import org.jgroups.util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ConcurrentHashMap;

public class Map implements StringMap {
	private final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

	@Override
	public boolean containsKey(String key) {
		return map.containsKey(key);
	}

	@Override
	public String get(String key) {
		String res = map.get(key);
		if (res == null) {
			res = "";
		}
		return res;
	}

	@Override
	public String put(String key, String value) {
		String res = map.put(key, value);
		if (res == null) {
			res = "";
		}
		return res;
	}

	@Override
	public String remove(String key) {
		String res = map.remove(key);
		if (res == null) {
			res = "";
		}
		return res;
	}

	@Override
	public void getState(OutputStream output) throws Exception {
		synchronized (map) {
			Util.objectToStream(map, new DataOutputStream(output));
		}
	}

	@Override
	public void setState(InputStream input) throws Exception {
		ConcurrentHashMap<String, String> receivedMap = (ConcurrentHashMap<String, String>)Util.objectFromStream(new DataInputStream(input));
		synchronized (this.map) {
			map.clear();
			map.putAll(receivedMap);
		}
	}

	@Override
	public String toString() {
		return "Map{" +
				"map=" + map +
				'}';
	}
}
