package AKKA.pong;

import AKKA.commom.Actor;
import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import protobuf.PingMensagem;
import protobuf.PongMensagem;

@Actor
public class AtorPong extends AbstractActor {

    LoggingAdapter loggingAdapter = Logging.getLogger(getContext().system(), this);

    private ActorSelection atorPing = getContext().actorSelection("akka.tcp://AkkaRemotePing@127.0.0.1:2558/user/AtorPing");
    PongMensagem pongMensagem = PongMensagem.newBuilder().setMensagem("pong").setNivel(3).build();

    private void printAndReturn(PingMensagem pingMsg) {
        loggingAdapter.info("Recebendo a mensagem: " + pingMsg.getMensagem() + " " + pingMsg.getNivel());
        getSender().tell(pongMensagem, getSelf());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(PingMensagem.class, this::printAndReturn)
                .build();
    }
}
