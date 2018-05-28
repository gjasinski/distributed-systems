package com.gjasinski.ds.akka.commons;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Response implements Serializable {
	private UUID requestUuid;
	private CommandType commandType;
	private String responsePayload;

	public Response(UUID requestUuid, CommandType commandType, String responsePayload) {
		this.requestUuid = requestUuid;
		this.commandType = commandType;
		this.responsePayload = responsePayload;
	}

	public Response() {
	}

	public UUID getRequestUuid() {
		return requestUuid;
	}

	public void setRequestUuid(UUID requestUuid) {
		this.requestUuid = requestUuid;
	}

	public CommandType getCommandType() {
		return commandType;
	}

	public void setCommandType(CommandType commandType) {
		this.commandType = commandType;
	}

	public String getResponsePayload() {
		return responsePayload;
	}

	public void setResponsePayload(String responsePayload) {
		this.responsePayload = responsePayload;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Response response = (Response) o;
		return Objects.equals(requestUuid, response.requestUuid) &&
				commandType == response.commandType &&
				Objects.equals(responsePayload, response.responsePayload);
	}

	@Override
	public int hashCode() {

		return Objects.hash(requestUuid, commandType, responsePayload);
	}

	@Override
	public String toString() {
		return "Response{" +
				"requestUuid=" + requestUuid +
				", commandType=" + commandType +
				", responsePayload='" + responsePayload + '\'' +
				'}';
	}
}
