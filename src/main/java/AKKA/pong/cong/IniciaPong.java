package AKKA.pong.cong;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class IniciaPong {

    private ActorRef actorRef;

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private SpringExtension springExtension;

    @PostConstruct
    void iniciarPong(){
        createSupercisorActor();
    }

    private void createSupercisorActor() {
        actorRef = actorSystem.actorOf(springExtension.props(SupervisorPong.class), "supervisorPong");
    }
}
