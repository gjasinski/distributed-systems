package com.gjasinski.ds.akka.client;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.gjasinski.ds.akka.commons.Command;
import com.gjasinski.ds.akka.commons.CommandType;
import com.gjasinski.ds.akka.commons.Response;
//import com.gjasinski.ds.akka.commons.ResponsePayload;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.UUID;

public class Client extends AbstractActor {
	public static final String REMOTE_ACTOR = "akka.tcp://remote_system@127.0.0.1:3552/user/remote";
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public static void main(String[] args) throws Exception {

		// config
		File configFile = new File("remote_app.conf");
		Config config = ConfigFactory.parseFile(configFile);

		// create actor system & actors
		final ActorSystem system = ActorSystem.create("local_system", config);
		final ActorRef local = system.actorOf(Props.create(Client.class), "local");

		// interaction
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("name:");
		String name = br.readLine();
		while (true) {
			System.out.println("s - search; o - order; d - download; q - quit");
			String line = br.readLine();
			if (line.equals("q")) {
				break;
			}
			System.out.println("title: ");
			String title = br.readLine();
			System.out.println("author:");
			String author = br.readLine();
			switch (line){
				case "s": createSearchQuery(local, name, title, author);
					break;
				case "o": createOrderQuery(local, name, title, author);
					break;
				case "d": createDownloadQuery(local, name,title, author);
					break;
			}

			local.tell(line, null);
		}

		system.terminate();
	}

	private static void createDownloadQuery(ActorRef local, String name, String title, String author) {
		UUID uuid = UUID.randomUUID();
		Command command = new Command(uuid, name, CommandType.STREAM, title, author);
		local.tell(command, null);
	}

	private static void createOrderQuery(ActorRef local, String name, String title, String author) {
		UUID uuid = UUID.randomUUID();
		Command command = new Command(uuid, name, CommandType.ORDER, title, author);
		local.tell(command, null);
	}

	private static void createSearchQuery(ActorRef local, String name, String title, String author) {
		UUID uuid = UUID.randomUUID();
		Command command = new Command(uuid, name, CommandType.SEARCH, title, author);
		local.tell(command, null);
	}

	@Override
	public AbstractActor.Receive createReceive() {
		return receiveBuilder()
				.match(String.class, System.out::println)
				.match(Command.class, c -> getContext().actorSelection(REMOTE_ACTOR).tell(c, getSelf()))
				.match(Response.class, r ->System.out.println(r.getResponsePayload()))
				.build();
	}

}
