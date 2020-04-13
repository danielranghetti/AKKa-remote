package AKKA.pong;


import AKKA.configuracao.SpringExtension;
import AKKA.configuracao.SpringProps;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class StartPong {
    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        System.getProperties().put( "server.port", 8080);
        SpringApplication.run(StartPong.class, args);
    }


    @PostConstruct
    void init() {
        // Criação de um Actor System, container Akka.
        ActorSystem system = ActorSystem.create("AkkaRemotePong", ConfigFactory.load().getConfig("AkkaRemotePong"));
        SpringExtension.getInstance().get(system).initialize(context);

        system.actorOf(Props.create(SupervisorAtorPong.class), "AtorPong");

        system.getWhenTerminated();
    }
}
