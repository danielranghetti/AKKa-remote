package AKKA.pong;

import AKKA.configuracao.Actor;
import AKKA.mensagem.Mensagem;
import akka.actor.ActorSelection;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.io.Serializable;

@Actor
public class AtorPong extends UntypedAbstractActor implements Serializable {

    LoggingAdapter loggingAdapter = Logging.getLogger(getContext().system(), this);

    private ActorSelection atorPing = getContext().actorSelection("akka.tcp://AkkaRemotePing@127.0.0.1:2558/user/AtorPing");

    public void onReceive(Object msg) throws Exception {
        if (msg instanceof Mensagem.PingMsg) {
            //pegar a mensagem do AtorPing
            Mensagem.PingMsg atorPing = (Mensagem.PingMsg) msg;
            //mostra a mensagem enviado do AtorPing para o AtorPong no console
            loggingAdapter.info("Recebendo a mensagem: " + atorPing.getMensagem() + " " + atorPing.getNivel());
            //inforna a mensagem que o AtorPong esta passando
            getSender().tell(new Mensagem.PongMsg("Pong", 3), getSelf());
        } else {
            unhandled(msg);
        }
    }
}
