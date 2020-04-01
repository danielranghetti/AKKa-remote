package AKKA.ping;

import AKKA.mensagem.Mensagem;
import akka.actor.ActorSelection;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class AtorPing extends UntypedAbstractActor {
    LoggingAdapter loggingAdapter = Logging.getLogger(getContext().system(), this);

    private ActorSelection atorPong = getContext().actorSelection("akka.tcp://AkkaRemotePong@127.0.0.1:2556/user/AtorPong");


    public void onReceive(Object msg) throws Exception {
        if (msg instanceof Mensagem.Iniciar) {
            loggingAdapter.info("Iniciando o ping-pong");
            //setando a msg do AtorPing para a AtorPong
            atorPong.tell(new Mensagem.PingMsg("Ping"), getSelf());
            //verificar ser a msg é do AtorPong
        } else if (msg instanceof Mensagem.PongMsg) {
            //pega a msg do AtorPong
            Mensagem.PongMsg atorPong = (Mensagem.PongMsg) msg;
            //mostra a msg enviada do AtorPong para o AtorPing no console
            loggingAdapter.info("Recebendo mensagem: " + atorPong.getMensagem());


        }
    }

}

