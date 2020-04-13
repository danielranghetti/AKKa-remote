package AKKA.ping;

import AKKA.configuracao.Actor;
import akka.actor.ActorSelection;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import protobuf.PingMensagem;
import protobuf.PongMensagem;

@Actor
public class AtorPing extends UntypedAbstractActor {

    public static class Iniciar {
    }

    LoggingAdapter loggingAdapter = Logging.getLogger(getContext().system(), this);

    private ActorSelection atorPong = getContext().actorSelection("akka.tcp://AkkaRemotePong@127.0.0.1:2556/user/AtorPong");


    public void onReceive(Object msg) throws Exception {
        if (msg instanceof Iniciar) {
            loggingAdapter.info("Iniciando o ping-pong");
            //setando a msg do AtorPing para a AtorPong
            atorPong.tell(PingMensagem.newBuilder().setMensagem("ping").build(), getSelf());
            //verificar ser a msg é do AtorPong
        } else if (msg instanceof PongMensagem) {
            //pega a msg do AtorPong
            PongMensagem atorPong = (PongMensagem) msg;
            //mostra a msg enviada do AtorPong para o AtorPing no console
            loggingAdapter.info("Recebendo mensagem: " + atorPong.getMensagem());
        }
    }

}

