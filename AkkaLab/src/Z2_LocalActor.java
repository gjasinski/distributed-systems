import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Z2_LocalActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                 // TODO
                .match(String.class, s -> {
                    if (s.startsWith("local")) {
                        getContext().actorSelection("akka.tcp://remote_system@127.0.0.1:3552/user/remote").tell(s, getSelf());
                    } else if (s.startsWith("result")) {
                        System.out.println(s.substring(6));              // result from child
                    }
                })
                .build();
    }

//    @Override
//    public void preStart(){
//        getContext().actorSelection("akka.tcp://systemName@127.0.0.1:3552/user/actorName").tell(...)
//    }
}
