package com.gjasinski.ds.akka.commons;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Command implements Serializable {
	private UUID uuid;
	private String name;
	private CommandType commandType;
	private String title;
	private String author;

	public Command(UUID uuid, String name, CommandType commandType, String title, String author) {
		this.uuid = uuid;
		this.name = name;
		this.commandType = commandType;
		this.title = title;
		this.author = author;
	}

	public Command() {
	}

	public UUID getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public CommandType getCommandType() {
		return commandType;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Command command = (Command) o;
		return Objects.equals(uuid, command.uuid) &&
				Objects.equals(name, command.name) &&
				commandType == command.commandType &&
				Objects.equals(title, command.title) &&
				Objects.equals(author, command.author);
	}

	@Override
	public int hashCode() {

		return Objects.hash(uuid, name, commandType, title, author);
	}

	@Override
	public String toString() {
		return "Command{" +
				"uuid=" + uuid +
				", name='" + name + '\'' +
				", commandType=" + commandType +
				", title='" + title + '\'' +
				", author='" + author + '\'' +
				'}';
	}
}
