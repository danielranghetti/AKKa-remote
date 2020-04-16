package AKKA.ping.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import protobuf.Iniciar;

@Service
public class IniciarPing {

//	private String supervisorName = "supervisorAtorPing";
	
    private ActorRef actorRef;

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private SpringExtension springExtension;

    @PostConstruct
    void iniciar(){
        createSupercisorActor();
        iniciandoActor();
    }

    private void createSupercisorActor() {
        actorRef = actorSystem.actorOf(springExtension.props(SupervisorPing.class), "supervisor");
    }

    private void iniciandoActor(){
        actorRef.tell(Iniciar.newBuilder().build(), ActorRef.noSender());
    }


}
