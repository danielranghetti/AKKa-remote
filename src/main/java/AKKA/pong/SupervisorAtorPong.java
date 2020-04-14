package AKKA.pong;

import AKKA.configuracao.Actor;
import akka.actor.*;
import akka.japi.Function;
import akka.japi.pf.FI;
import protobuf.PingMensagem;
import scala.concurrent.duration.Duration;

@Actor
public class SupervisorAtorPong extends AbstractActor {

    private ActorRef atorPong = getContext().actorOf(Props.create(AtorPong.class), "AtorPongPrimeiro");
    private ActorRef atorPongSegundo = getContext().actorOf(Props.create(AtorPong.class), "AtorPongSegundo");
    private ActorRef atorPongTerceiro = getContext().actorOf(Props.create(AtorPong.class), "AtorPongTerceiro");

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
                        atorPong.forward(any, SupervisorAtorPong.this.getContext());
                    } else if (mensagem.equalsIgnoreCase("ping2")) {
                        atorPongSegundo.forward(any, SupervisorAtorPong.this.getContext());
                    } else if (mensagem.equalsIgnoreCase("ping3")) {
                        atorPongTerceiro.forward(any, SupervisorAtorPong.this.getContext());
                    }
                }
            }

        }).build();
    }
}

