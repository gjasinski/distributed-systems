import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Z2_RemoteActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    if (s.startsWith("local")) {
                        s = s.substring(5).toUpperCase();
                        System.out.println(s);
                        getContext().actorSelection("akka.tcp://local_system@127.0.0.1:2552/user/local").tell("result" + s, getSelf());
                    }
                })
                .build();
    }
}
