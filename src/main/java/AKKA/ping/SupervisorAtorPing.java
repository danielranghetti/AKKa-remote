package AKKA.ping;

import AKKA.configuracao.Actor;
import AKKA.configuracao.SpringExtension;
import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import akka.japi.pf.FI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import protobuf.Iniciar;
import protobuf.PingMensagem;
import protobuf.PongMensagem;
import scala.concurrent.duration.Duration;

@Actor

@Service
@Scope(value= BeanDefinition.SCOPE_PROTOTYPE)
public class SupervisorAtorPing extends AbstractActor {
    LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    ActorRef atorPing;
    ActorRef atorPingSegundo;
    ActorRef atorPingTerceiro;

    @Autowired
    private SpringExtension springExtension;

    public void preStart() throws Exception {
        super.preStart();
        atorPing = getContext().actorOf(springExtension.props("AtorPingPrimeiro"));
        atorPingSegundo = getContext().actorOf(springExtension.props("AtorPingSegundo"));
        atorPingTerceiro = getContext().actorOf(springExtension.props("atorPingTerceiro"));
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
