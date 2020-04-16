package AKKA.ping;

import AKKA.configuracao.SpringExtension;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import protobuf.Iniciar;

import javax.annotation.PostConstruct;

@Component
public class Teste {

    private ActorRef actorRef;

//    @Autowired
    private ActorSystem actorSystem;

//    @Autowired
    private SpringExtension springExtension;

    @PostConstruct
    void iniciar(){
        createSupercisorActor();
    }

    private void createSupercisorActor() {
        actorRef = actorSystem.actorOf(springExtension.props("SupervisorAtorPing"), "atorPing");
        iniciandoActor();

    }

    private void iniciandoActor(){
        actorRef.tell(Iniciar.newBuilder().build(), ActorRef.noSender());
    }


}
