package com.gjasinski.distributesystems.distributedhashmap;

import java.io.InputStream;
import java.io.OutputStream;

public class Operation {
	private final StringMap map;
	private final SynchronisationChannel synchronisationChannel;

	Operation(StringMap map, SynchronisationChannel synchronisationChannel) {
		this.map = map;
		this.synchronisationChannel = synchronisationChannel;
	}


	byte[] makeOperation(MapCommunication.MapOperation operation) {
		switch (operation.getType()) {
			case CONTAINS_KEY:
				return createBooleanResult(map.containsKey(operation.getKey()));
			case GET:
				return createStringResult(map.get(operation.getKey()));
			case PUT:
				return createStringResult(map.put(operation.getKey(), operation.getValue()));
			case REMOVE:
				return createStringResult(map.remove(operation.getKey()));
			default:
				throw new IllegalArgumentException("Not implemented operation type" + operation.getType());
		}
	}

	void propagateChanges(MapCommunication.MapOperation operation) throws Exception {
		if(shouldPropagateChanges(operation)){
			synchronisationChannel.send(operation);
		}
	}

	private boolean shouldPropagateChanges(MapCommunication.MapOperation operation) {
		return operation.getType().equals(MapCommunication.MapOperation.OperationType.PUT) ||
				operation.getType().equals(MapCommunication.MapOperation.OperationType.REMOVE);
	}


	private byte[] createBooleanResult(boolean result) {
		return MapCommunication.BooleanResult.newBuilder()
				.setResult(result)
				.build()
				.toByteArray();
	}


	private byte[] createStringResult(String result) {
		return MapCommunication.StringResult.newBuilder()
				.setTesult(result)
				.build()
				.toByteArray();
	}

	public void getState(OutputStream output) throws Exception {
		map.getState(output);
	}

	public void setState(InputStream input) throws Exception {
		map.setState(input);
	}
}
