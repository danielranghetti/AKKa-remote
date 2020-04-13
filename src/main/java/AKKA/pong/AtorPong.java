package AKKA.pong;

import AKKA.configuracao.Actor;
import akka.actor.ActorSelection;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import protobuf.PingMensagem;
import protobuf.PongMensagem;

import java.io.Serializable;

@Actor
public class AtorPong extends UntypedAbstractActor implements Serializable {

    LoggingAdapter loggingAdapter = Logging.getLogger(getContext().system(), this);

    private ActorSelection atorPing = getContext().actorSelection("akka.tcp://AkkaRemotePing@127.0.0.1:2558/user/AtorPing");

    public void onReceive(Object msg) throws Exception {
        if (msg instanceof PingMensagem) {
            //pegar a mensagem do AtorPing
            PingMensagem atorPing = (PingMensagem) msg;
            //mostra a mensagem enviado do AtorPing para o AtorPong no console
            loggingAdapter.info("Recebendo a mensagem: " + atorPing.getMensagem());
            //inforna a mensagem que o AtorPong esta passando
            getSender().tell(PongMensagem.newBuilder().setMensagem("Pong").build(), getSelf());
        } else {
            unhandled(msg);
        }
    }
}
