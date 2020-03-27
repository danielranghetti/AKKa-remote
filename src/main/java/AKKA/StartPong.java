package AKKA;


import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

public class StartPong {

    public static void main(String[] args) {
        // Criação de um Actor System, container Akka.
        ActorSystem system = ActorSystem.create("AkkaRemoteServer", ConfigFactory.load().getConfig("AkkaRemoteServer"));
        system.actorOf(Props.create(AtorPong.class), "AtorPong");
        system.getWhenTerminated();
    }
}
