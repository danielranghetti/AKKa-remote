package AKKA.ping;

import AKKA.configuracao.Actor;
import AKKA.mensagem.Mensagem;
import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import akka.japi.pf.FI;
import protobuf.Iniciar;
import protobuf.PingMensagem;
import protobuf.PongMensagem;
import scala.concurrent.duration.Duration;

@Actor
public class SupervisorAtorPing extends AbstractActor {
    LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private static SupervisorStrategy strategy = new OneForOneStrategy(3,
            Duration.create("10 second"), new Function<Throwable, SupervisorStrategy.Directive>() {
        public SupervisorStrategy.Directive apply(Throwable t) {
            return OneForOneStrategy.resume();

        }
    });


    final ActorRef atorPing = getContext().actorOf(Props.create(AtorPing.class), "AtorPingPrimeiro");
    final ActorRef atorPingSegundo = getContext().actorOf(Props.create(AtorPing.class), "AtorPingSegundo");
    final ActorRef atorPingTerceiro = getContext().actorOf(Props.create(AtorPing.class), "AtorPingTerceiro");


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
                        atorPing.forward(any, SupervisorAtorPing.this.getContext());
                    } else if (mensagem.equalsIgnoreCase("pong2")) {
                        atorPingSegundo.forward(any, SupervisorAtorPing.this.getContext());
                    } else if (mensagem.equalsIgnoreCase("pong3")) {
                        atorPingTerceiro.forward(any, SupervisorAtorPing.this.getContext());
                    }
                }
            }


        }).build();
    }


}
