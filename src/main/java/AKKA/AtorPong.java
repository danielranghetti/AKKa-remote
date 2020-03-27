package AKKA;

import akka.actor.ActorSelection;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.io.Serializable;

public class AtorPong extends UntypedAbstractActor implements Serializable {

    LoggingAdapter loggingAdapter = Logging.getLogger(getContext().system(), this);

    private ActorSelection atorPing = getContext().actorSelection("akka.tcp://AkkaRemotePing@127.0.0.1:2558/user/AtorPing");

    public void onReceive(Object msg) throws Exception {
        if (msg instanceof Mensagem.Iniciar) {
//            loggingAdapter.info("Iniciando o ping-pong");
//            //setando a msg do AtorPing para a AtorPong
//            atorPing.tell(new Mensagem.PingMsg("Ping novo"), getSelf());
//            //verificar ser a msg é do AtorPong
            loggingAdapter.info("ator recebeu inicar quando não deveria");
        } else if (msg instanceof Mensagem.PingMsg) {
            //pegar a mensagem do AtorPing
            Mensagem.PingMsg atorPing = (Mensagem.PingMsg) msg;
            //mostra a mensagem enviado do AtorPing para o AtorPong no console
            loggingAdapter.info("Recebendo a mensagem: " + atorPing.getMensagem());
            //inforna a mensagem que o AtorPong esta passando
                getSender().tell(new Mensagem.PongMsg("Pong"), getSelf());
        } else {
            unhandled(msg);
        }
    }
}
