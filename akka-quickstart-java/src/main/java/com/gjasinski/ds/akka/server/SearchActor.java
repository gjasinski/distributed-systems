package com.gjasinski.ds.akka.server;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.gjasinski.ds.akka.commons.Command;
import com.gjasinski.ds.akka.commons.Response;
//import com.gjasinski.ds.akka.commons.ResponsePayload;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

class SearchActor extends AbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private static final int INTERVAL = 1_000;
	private Path database;

	public SearchActor(String database) {
		this.database = FileSystems.getDefault().getPath(database);
	}

	@Override
	public AbstractActor.Receive createReceive() {
		return receiveBuilder()
				.match(Command.class, c -> {
					synchronized (this) {
						System.out.println(database);
						String s = searchBook(c.getTitle(), c.getAuthor());
						Response response = new Response(c.getUuid(), c.getCommandType(), s);
						getSender().tell(response, getSelf());
					}
				})
				.matchAny(o -> log.info("received unknown message"))
				.build();
	}

	private String searchBook(String title, String author) {
		Charset charset = Charset.forName("US-ASCII");
		try (BufferedReader reader = Files.newBufferedReader(database, charset)) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] record = line.split(";");
				if(compareTitles(record[0], title, record[1], author)){
					return line;
				}
				Thread.sleep(INTERVAL);
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "Not found";
	}

	private boolean compareTitles(String title1, String title2, String author1, String author2){
		return title1.equals(title2) && author1.equals(author2);
	}

}
