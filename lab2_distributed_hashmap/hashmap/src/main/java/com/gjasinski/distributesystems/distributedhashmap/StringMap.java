package com.gjasinski.distributesystems.distributedhashmap;

import java.io.InputStream;
import java.io.OutputStream;

public interface StringMap extends SimpleStringMap {
	boolean containsKey(String key);

	String get(String key);

	String put(String key, String value);

	String remove(String key);

	void getState(OutputStream output) throws Exception;

	void setState(InputStream input) throws Exception;
}
