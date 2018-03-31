package com.gjasinski.distributesystems.distributedhashmap;

import java.io.InputStream;
import java.io.OutputStream;

public class VerboseMap implements StringMap {

	private final Map map;

	VerboseMap(Map map) {
		this.map = map;
	}

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
		System.out.println("PUT " + key + ": " + value +" \nBEFORE: " + toString());
		String put = map.put(key, value);
		System.out.println("PUT AFTER: " + toString());
		System.out.println();
		return put;
	}

	@Override
	public String remove(String key) {
		System.out.println("REMOVE " + key + " \nBEFORE: " + toString());
		String remove = map.remove(key);
		System.out.println("REMOVE AFTER: " + toString());
		System.out.println();
		return remove;
	}

	@Override
	public void getState(OutputStream output) throws Exception {
		map.getState(output);
	}

	@Override
	public void setState(InputStream input) throws Exception {
		map.setState(input);
		System.out.println("Set map state " + toString());
	}

	@Override
	public String toString() {
		return map.toString();
	}
}
