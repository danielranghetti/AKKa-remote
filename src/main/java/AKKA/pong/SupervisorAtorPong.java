package AKKA.pong;

import AKKA.configuracao.Actor;
import akka.actor.*;
import akka.japi.Function;
import akka.japi.pf.FI;
import scala.concurrent.duration.Duration;

@Actor
public class SupervisorAtorPong extends AbstractActor {

    private ActorRef childActor = getContext().actorOf(Props.create(AtorPong.class), "AtorPong");

    private static SupervisorStrategy strategy = new OneForOneStrategy(3,
            Duration.create("10 second"), new Function<Throwable, SupervisorStrategy.Directive>() {
        public SupervisorStrategy.Directive apply(Throwable t) {
            return OneForOneStrategy.resume();
        }
    });

    @Override
    public SupervisorStrategy supervisorStrategy(){
        return strategy;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchAny(new FI.UnitApply<Object>() {
            @Override
            public void apply(Object any) throws Exception {
                childActor.forward(any, SupervisorAtorPong.this.getContext());
            }
        }).build();
    }
}

