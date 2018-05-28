import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Z1_DivWorker extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private int counter = 0;
    @Override
    public Receive createReceive() {
            return receiveBuilder()
                    .match(String.class, s -> {
                        String result = Multiply(s);
                        getSender().tell("result: " + result, getSelf());
                    })
                    .matchAny(o -> log.info("received unknown message"))
                    .build();
    }
    
    private String Multiply(String s){
        String[] split = s.split(" ");
        int a = Integer.parseInt(split[1]);
        int b = Integer.parseInt(split[2]);
        return (a/b) + " counter: " + counter++;
    }
}
