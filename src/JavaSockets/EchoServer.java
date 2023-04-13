package JavaSockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    public static void startServer(Integer porta) throws IOException {
        // apro una porta tcp
        ServerSocket serverSocket = new ServerSocket(porta);
        System.out.println("server socket in ascolto sulla porta: " + porta);

        // resto in attesa di una connessione
        Socket socket = serverSocket.accept();
    }

    public static void main(String[] args) throws Exception {

        try {
            startServer(4567);
        } catch (Exception e) {
            System.out.println("errore nell'avvio del server socket");
        }
        
    }

}