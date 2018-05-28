package com.gjasinski.ds.akka.server;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import akka.routing.RoundRobinPool;
import com.gjasinski.ds.akka.commons.Command;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.duration.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import static akka.actor.SupervisorStrategy.restart;

public class Bookshop extends AbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public static void main(String[] args) throws Exception {
		// config
		File configFile = new File("remote_app2.conf");
		Config config = ConfigFactory.parseFile(configFile);
		final ActorSystem system = ActorSystem.create("remote_system", config);
		final ActorRef remote = system.actorOf(Props.create(Bookshop.class), "remote");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String line = br.readLine();
			if (line.equals("q")) {
				break;
			}
			remote.tell(line, null);
		}

		system.terminate();
	}

	@Override
	public void preStart() throws Exception {
		context().actorOf(Props.create(OrderSupervisor.class), "orderSupervisor");
		context().actorOf(Props.create(StreamActor.class), "streamActor");
		context().actorOf(Props.create(SearchSupervisor.class).withRouter(new RoundRobinPool(5)), "searchSupervisor");
	}


	@Override
	public AbstractActor.Receive createReceive() {
		return receiveBuilder()
				.match(Command.class, c -> {
					applyAction(c, getSender());
				})
				.match(String.class, s -> System.out.println(s))
				.build();
	}

	private void applyAction(Command c, ActorRef sender) {
		switch (c.getCommandType()) {
			case ORDER:
				context().child("orderSupervisor").get().tell(c, sender);
				break;
			case SEARCH:
				context().child("searchSupervisor").get().tell(c, sender);
				break;
			case STREAM:
				context().child("streamActor").get().tell(c, sender);
				break;
			default:
				throw new IllegalArgumentException("Not implemented enum");
		}
	}

	private static SupervisorStrategy strategy
			= new OneForOneStrategy(10, Duration.create("1 minute"),
			DeciderBuilder.matchAny(o -> restart()).
					build());

	@Override
	public SupervisorStrategy supervisorStrategy() {
		return strategy;
	}
}
