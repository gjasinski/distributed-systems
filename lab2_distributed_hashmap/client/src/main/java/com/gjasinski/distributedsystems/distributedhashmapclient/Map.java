package com.gjasinski.distributedsystems.distributedhashmapclient;

import com.gjasinski.distributedsystems.distributedhashmapclient.MapCommunication.MapOperation;
import com.gjasinski.distributedsystems.distributedhashmapclient.MapCommunication.MapOperation.OperationType;

import java.io.IOException;

public class Map implements SimpleStringMap{
	private final Channel channel;


	Map(Channel channel) {
		this.channel = channel;
	}

	public boolean containsKey(String key) throws IOException {
		MapOperation operation = createKeyMapOperation(key, OperationType.CONTAINS_KEY);
		channel.makeOperation(operation);
		return channel.receiveBooleanResult().getResult();
	}

	public String get(String key) throws IOException {
		MapOperation operation = createKeyMapOperation(key, OperationType.GET);
		channel.makeOperation(operation);
		return channel.receiveStringResult().getTesult();
	}

	public String put(String key, String value) throws IOException {
		MapOperation operation = MapOperation.newBuilder()
				.setType(MapOperation.OperationType.PUT)
				.setKey(key)
				.setValue(value)
				.build();
		channel.makeOperation(operation);
		return channel.receiveStringResult().getTesult();
	}

	public String remove(String key) throws IOException {
		MapOperation operation = createKeyMapOperation(key, OperationType.REMOVE);
		channel.makeOperation(operation);
		return channel.receiveStringResult().getTesult();
	}

	private MapOperation createKeyMapOperation(String key, OperationType remove) {
		return MapOperation.newBuilder()
				.setType(remove)
				.setKey(key)
				.build();
	}
}
