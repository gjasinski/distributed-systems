package com.gjasinski.ds.akka.server;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import com.gjasinski.ds.akka.commons.Command;
import com.gjasinski.ds.akka.commons.Response;
import scala.concurrent.duration.Duration;

import static akka.actor.SupervisorStrategy.restart;

public class SearchSupervisor extends AbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private boolean receivedResponse;
	private ActorRef commandSender;

	@Override
	public AbstractActor.Receive createReceive() {
		return receiveBuilder()
				.match(Command.class, c -> {
					receivedResponse = false;
					commandSender = getSender();
					context().child("searchActor1").get().tell(c, getSelf());
					context().child("searchActor2").get().tell(c, getSelf());
				})
				.match(Response.class, r -> {
					System.out.println(r);
					if (!receivedResponse) {
						commandSender.tell(r, self());
						receivedResponse = true;
					}
				})
				.matchAny(o -> log.info("received unknown message"))
				.build();
	}

	@Override
	public void preStart() throws Exception {
		context().actorOf(Props.create(SearchActor.class, "src/main/resources/Database1"), "searchActor1");
		context().actorOf(Props.create(SearchActor.class, "src/main/resources/Database2"), "searchActor2");
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
