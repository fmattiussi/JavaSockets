package JavaSockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	// funzione per eseguire il sever
    public static void startServer(Integer porta) throws IOException {
    	
        // apro una porta tcp
        ServerSocket serverSocket = new ServerSocket(porta);
        System.out.println("server socket in ascolto sulla porta: " + porta);

        // resto in attesa di una connessione
        Socket socket = serverSocket.accept();
        
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream())); // salva in un buffer l'input del socket
        
        // esegue le operazioni finquando il socket è connesso
        while (socket.isConnected()) {
        	
        	System.out.println("messaggio dal client: " + input.readLine()); // mostra i messaggi ricevuti dal client
        }
        
    }

    public static void main(String[] args) throws Exception {

        try {
            startServer(4567); // la funzione viene inserita all'interno di una closure try/catch per gestire gli eventuali errori
        } catch (Exception e) {
            System.out.println("errore nell'avvio del server socket");
        }
        
    }

}