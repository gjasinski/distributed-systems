package com.gjasinski.ds.akka.server;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.gjasinski.ds.akka.commons.Command;
import com.gjasinski.ds.akka.commons.Response;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class StreamActor extends AbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private static final int INTERVAL = 1_000;


	@Override
	public AbstractActor.Receive createReceive() {
		return receiveBuilder()
				.match(Command.class, c -> {
					streamBook(getSender(), c);
				})
				.matchAny(o -> log.info("received unknown message"))
				.build();
	}

	private void streamBook(ActorRef sendTo, Command command) {
		Path book = FileSystems.getDefault().getPath("src/main/resources/" + command.getTitle());
		System.out.println(book);


		try (InputStream in = Files.newInputStream(book);
		     BufferedReader reader =
				     new BufferedReader(new InputStreamReader(in))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				Response response = new Response(command.getUuid(), command.getCommandType(), line);
				sendTo.tell(response, getSelf());
				Thread.sleep(INTERVAL);
			}
		} catch (Exception e) {
			Response response = new Response(command.getUuid(), command.getCommandType(), "Not found");
			sendTo.tell(response, getSelf());
		}

	}

}