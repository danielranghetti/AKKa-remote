package AKKA.pong;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PongApplication {

    public static void main(String[] args) {
        System.getProperties().put( "server.port", 8080);
        SpringApplication.run(PongApplication.class, args);
    }



}

