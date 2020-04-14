package AKKA.ping;

import AKKA.configuracao.Actor;
import AKKA.mensagem.Mensagem;
import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.event.Logging;
import akka.event.LoggingAdapter;

@Actor
public class AtorPing extends AbstractActor {
    LoggingAdapter loggingAdapter = Logging.getLogger(getContext().system(), this);

    private ActorSelection atorPong = getContext().actorSelection("akka.tcp://AkkaRemotePong@127.0.0.1:2556/user/AtorPong");

    Mensagem.PingMsg pingMsg = (new Mensagem.PingMsg("ping", 2));

    private void inicio(Mensagem.Iniciar inicia) {
        loggingAdapter.info("Iniciando o ping-pong");
        atorPong.tell(pingMsg, getSelf());
    }

    private void print(Mensagem.PongMsg pongMsg) {
        loggingAdapter.info("Recebendo mensagem: " + pongMsg.getMensagem() + " " + pongMsg.getNivel());
    }
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Mensagem.Iniciar.class, this::inicio)
                .match(Mensagem.PongMsg.class, this::print)
                .build();
    }


}

