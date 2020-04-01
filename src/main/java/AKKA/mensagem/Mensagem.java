package AKKA.mensagem;

import java.io.Serializable;

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
