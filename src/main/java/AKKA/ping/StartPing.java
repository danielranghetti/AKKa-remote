package AKKA.ping;


import AKKA.configuracao.SpringExtension;
import AKKA.configuracao.SpringProps;
import AKKA.mensagem.Mensagem;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.typesafe.config.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class StartPing {


    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        System.getProperties().put( "server.port", 8081);
        SpringApplication.run(StartPing.class, args);



    }

    @PostConstruct
    void iniciarPing() {
        // Criação de um Actor System, container Akka.
        ActorSystem system = ActorSystem.create("AkkaRemotePing",
                ConfigFactory.load().getConfig("AkkaRemotePing"));
        // Criando o ator
        SpringExtension.getInstance().get(system).initialize(context);
        ActorRef actorRef = system.actorOf(SpringProps.create(system,SupervisorAtorPing.class), "atorPing");
        // Enviando a mensagem ao ator
        actorRef.tell(new Mensagem.Iniciar(), ActorRef.noSender());
        system.getWhenTerminated();

    }
}

