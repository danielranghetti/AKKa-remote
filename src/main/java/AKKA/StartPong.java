package AKKA;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

public class StartPong {

    public static void main(String[] args) {
        // Criação de um Actor System, container Akka.
        ActorSystem system = ActorSystem.create("AkkaRemotePong", ConfigFactory.load().getConfig("AkkaRemotePong"));
        system.actorOf(Props.create(AtorPong.class), "AtorPong");
        //ActorRef actorRef = system.actorOf(Props.create(AtorPong.class), "AtorPong");
       //actorRef.tell(new AtorPong.Iniciar(), null);
        system.getWhenTerminated();
    }
}
