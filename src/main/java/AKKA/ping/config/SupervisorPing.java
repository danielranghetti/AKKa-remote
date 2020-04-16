package AKKA.ping.config;

import org.springframework.beans.factory.annotation.Autowired;

import AKKA.commom.Actor;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import akka.japi.pf.FI;
import protobuf.Iniciar;
import protobuf.PingMensagem;
import protobuf.PongMensagem;
import scala.concurrent.duration.Duration;

@Actor
public class SupervisorPing extends AbstractActor {

    ActorRef atorPing1;
    ActorRef atorPing2;
    ActorRef atorPing3;

    @Autowired
    private SpringExtension springExtension;

    public void preStart() throws Exception {
        super.preStart();
        atorPing1 = getContext().actorOf(springExtension.props("atorPing"));
        atorPing2 = getContext().actorOf(springExtension.props("atorPing"));
        atorPing3 = getContext().actorOf(springExtension.props("atorPing"));
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
                if (any instanceof Iniciar) {
                    ActorSelection atorPong = getContext().actorSelection("akka.tcp://AkkaRemotePong@127.0.0.1:2556/user/AtorPong");
                    PingMensagem pingMensagem = PingMensagem.newBuilder().setMensagem("ping").setNivel(1).build();
                    atorPong.tell(pingMensagem, getSelf());
                }
                if (any instanceof PongMensagem) {
                    PongMensagem msg = (PongMensagem) any;
                    String mensagem = msg.getMensagem() + msg.getNivel();
                    if (mensagem.equalsIgnoreCase("pong1")) {
                        atorPing1.forward(any, SupervisorPing.this.getContext());
                    } else if (mensagem.equalsIgnoreCase("pong2")) {
                    	atorPing2.forward(any, SupervisorPing.this.getContext());
                    } else if (mensagem.equalsIgnoreCase("pong3")) {
                    	atorPing3.forward(any, SupervisorPing.this.getContext());
                    }
                }
            }


        }).build();
    }


}
