package AKKA.ping.model;

import AKKA.commom.Actor;
import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import protobuf.Iniciar;
import protobuf.PingMensagem;
import protobuf.PongMensagem;

@Actor
public class AtorPing extends AbstractActor {
    LoggingAdapter loggingAdapter = Logging.getLogger(getContext().system(), this);

    private ActorSelection atorPong = getContext().actorSelection("akka.tcp://AkkaRemotePong@127.0.0.1:2556/user/AtorPong");

    PingMensagem pingMsg = PingMensagem.newBuilder().setMensagem("ping").setNivel(2).build();

    private void inicio(Iniciar inicia) {
        loggingAdapter.info("Iniciando o ping-pong");
        atorPong.tell(pingMsg, getSelf());
    }

    private void print(PongMensagem pongMsg) {
        loggingAdapter.info("Recebendo mensagem: " + pongMsg.getMensagem() + " " + pongMsg.getNivel());
    }
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Iniciar.class, this::inicio)
                .match(PongMensagem.class, this::print)
                .build();
    }


}

