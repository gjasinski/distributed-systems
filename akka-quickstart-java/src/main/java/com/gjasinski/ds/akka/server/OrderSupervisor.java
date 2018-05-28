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
import com.gjasinski.ds.akka.commons.CommandType;
import com.gjasinski.ds.akka.commons.Response;
import scala.concurrent.duration.Duration;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static akka.actor.SupervisorStrategy.restart;

public class OrderSupervisor extends AbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ActorRef commandSender;

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(Command.class, c -> {
					commandSender = getSender();
					context().child("orderActor").get().tell(c, getSelf());
				})
				.match(Response.class, r -> {
					commandSender.tell(r, self());
				})
				.matchAny(o -> log.info("received unknown message"))
				.build();
	}

	@Override
	public void preStart() throws Exception {
		context().actorOf(Props.create(OrderActor.class, "src/main/resources/Orders"), "orderActor");
//		context().actorOf(Props.create(OrderActor.class, "/home/grzegorz/git/distributed-systems/akka-quickstart-java/src/main/resources/Orders"), "orderActor");
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
