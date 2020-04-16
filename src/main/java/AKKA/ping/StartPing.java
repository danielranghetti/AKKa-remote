package AKKA.ping;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartPing {

    public static void main(String[] args) {
        System.getProperties().put("server.port", 8081);
        SpringApplication.run(StartPing.class, args);
    }
}

