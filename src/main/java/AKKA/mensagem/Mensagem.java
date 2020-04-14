package AKKA.mensagem;

import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class Mensagem {


    public static class Iniciar {
    }

    public static class PongMsg implements Serializable {
        private final String mensagem;
        private final int nivel;

        public PongMsg(String mensagem, int nivel) {
            this.mensagem = mensagem;
            this.nivel = nivel;
        }

        public String getMensagem() {
            return mensagem;
        }

        public int getNivel() {
            return nivel;
        }
    }

    public static class PingMsg implements Serializable {
        private final String mensagem;
        private final int nivel;

        public PingMsg(String mensagem, int nivel) {
            this.mensagem = mensagem;
            this.nivel = nivel;
        }

        public String getMensagem() {
            return mensagem;
        }

        public int getNivel() {
            return nivel;
        }
    }

}
