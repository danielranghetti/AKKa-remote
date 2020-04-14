package AKKA.pong;

import AKKA.configuracao.Actor;
import AKKA.mensagem.Mensagem;
import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.event.Logging;
import akka.event.LoggingAdapter;

@Actor
public class AtorPong extends AbstractActor {

    LoggingAdapter loggingAdapter = Logging.getLogger(getContext().system(), this);

    private ActorSelection atorPing = getContext().actorSelection("akka.tcp://AkkaRemotePing@127.0.0.1:2558/user/AtorPing");
    Mensagem.PongMsg pongMsg = new Mensagem.PongMsg("Pong" , 3);

    private void printAndReturn(Mensagem.PingMsg pingMsg) {
        loggingAdapter.info("Recebendo a mensagem: " + pingMsg.getMensagem() + " " + pingMsg.getNivel());
        getSender().tell(pongMsg, getSelf());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(Mensagem.PingMsg.class, this::printAndReturn)
                .build();
    }
}
