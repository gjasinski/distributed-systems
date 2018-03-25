package com.gjasinski.distributesystems.distributedhashmap;

public class Map implements SimpleStringMap {
	@Override
	public boolean containsKey(String key) {
		return false;
	}

	@Override
	public String get(String key) {
		return "";
	}

	@Override
	public String put(String key, String value) {
		return "";
	}

	@Override
	public String remove(String key) {
		return "";
	}
}
