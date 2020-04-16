package AKKA.pong.cong;

import AKKA.commom.Actor;
import AKKA.ping.config.SpringExtension;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.japi.Function;
import akka.japi.pf.FI;
import org.springframework.beans.factory.annotation.Autowired;
import protobuf.PingMensagem;
import scala.concurrent.duration.Duration;

@Actor
public class SupervisorPong extends AbstractActor {

    private ActorRef atorPong;
    private ActorRef atorPongSegundo;
    private ActorRef atorPongTerceiro;

    @Autowired
    private SpringExtension springExtension;

    public void preStart() throws Exception {
        super.preStart();
        atorPong = getContext().actorOf(Props.create(AtorPong.class), "AtorPong");
        atorPongSegundo = getContext().actorOf(Props.create(AtorPong.class), "AtorPong");
        atorPongTerceiro = getContext().actorOf(Props.create(AtorPong.class), "AtorPong");
    }

    private static SupervisorStrategy strategy = new OneForOneStrategy(3,
            Duration.create("10 second"), new Function<Throwable, SupervisorStrategy.Directive>() {
        public SupervisorStrategy.Directive apply(Throwable t) {
            return OneForOneStrategy.resume();
        }
    });

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchAny(new FI.UnitApply<Object>() {
            @Override
            public void apply(Object any) throws Exception {
                if (any instanceof PingMensagem) {
                    PingMensagem msg = (PingMensagem) any;
                    String mensagem = msg.getMensagem() + msg.getNivel();
                    if (mensagem.equalsIgnoreCase("ping1")) {
                        atorPong.forward(any, SupervisorPong.this.getContext());
                    } else if (mensagem.equalsIgnoreCase("ping2")) {
                        atorPongSegundo.forward(any, SupervisorPong.this.getContext());
                    } else if (mensagem.equalsIgnoreCase("ping3")) {
                        atorPongTerceiro.forward(any, SupervisorPong.this.getContext());
                    }
                }
            }

        }).build();
    }
}

