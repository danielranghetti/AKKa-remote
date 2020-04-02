package AKKA.mensagem;

import org.springframework.stereotype.Service;

import java.io.Serializable;
@Service
public class Mensagem {


    public static class Iniciar {
    }

    public static class PongMsg implements Serializable {
        private final String mensagem;

        public PongMsg(String mensagem) {
            this.mensagem = mensagem;
        }

        public String getMensagem() {
            return mensagem;
        }
    }



    public static class PingMsg implements Serializable {
        private final String mensagem;

        public PingMsg(String mensagem) {
            this.mensagem = mensagem;
        }

        public String getMensagem() {
            return mensagem;
        }
    }
}
