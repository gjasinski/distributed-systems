package com.gjasinski.distributedsystems.distributedhashmapclient;

import java.io.IOException;

public interface SimpleStringMap {
	boolean containsKey(String key) throws IOException;

	String get(String key) throws IOException;

	String put(String key, String value) throws IOException;

	String remove(String key) throws IOException;
}
