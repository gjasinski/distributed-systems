import akka.actor.AbstractActor;

public class Z3_SinkActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchAny(o -> System.out.println(o.toString()))
                .build();
    }
    // TODO
}



