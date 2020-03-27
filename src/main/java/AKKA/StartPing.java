package AKKA;


        import akka.actor.ActorRef;
        import akka.actor.ActorSystem;
        import akka.actor.Props;
        import com.typesafe.config.ConfigFactory;

public class StartPing {
    public static void main(String[] args) {
        // Criação de um Actor System, container Akka.
        ActorSystem system = ActorSystem.create("AkkaRemoteClient" ,ConfigFactory.load().getConfig("AkkaRemoteClient"));
        // Criando o ator
        ActorRef actorRef = system.actorOf(Props.create(AtorPing.class));
        // Enviando a mensagem ao ator
        actorRef.tell(new Mensagem.Iniciar(), null);
        system.getWhenTerminated();


    }
}
