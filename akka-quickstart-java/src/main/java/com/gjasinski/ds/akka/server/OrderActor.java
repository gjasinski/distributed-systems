package com.gjasinski.ds.akka.server;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.gjasinski.ds.akka.commons.Command;
import com.gjasinski.ds.akka.commons.Response;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class OrderActor extends AbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private String database;

	public OrderActor(String database) {
		this.database = database;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(Command.class, c -> {
					String s = saveOrder(c.getName(), c.getTitle(), c.getAuthor());
					Response response = new Response(c.getUuid(), c.getCommandType(), s);
					getSender().tell(response, getSelf());
				})
				.matchAny(o -> log.info("received unknown message"))
				.build();
	}

	private String saveOrder(String name, String title, String author) {
		String order = name + ";" + title + ";" + author;
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(database, true));
			bufferedWriter.write(order + "\n");
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Ordered: " + order;
	}

}
