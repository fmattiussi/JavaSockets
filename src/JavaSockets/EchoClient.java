package JavaSockets;

import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.PrintWriter;

public class EchoClient {
	
	public static void startClient(String ip, Integer port) throws IOException {
		
		Socket socket = new Socket(ip, port); // inizializzazione del socket
		System.out.println("connessione stabilita");
		
		Scanner socketIn = new Scanner(socket.getInputStream()); // input stream del socket
		PrintWriter socketOut = new PrintWriter(socket.getOutputStream()); // output stream del socket
		
		Scanner stdin = new Scanner(System.in);	// scanner per leggere i nuovi comandi in input
		
		try {
			
			// vengono inviati i messaggi al server
			while (true) {
				String inputLine = stdin.nextLine(); // viene letta la stringa dal terminale
				socketOut.println(inputLine); // viene inviato il messaggio al server tramite il socket
				socketOut.flush(); // viene rilasciato il stream in output del socket
				
				String socketLine = socketIn.nextLine();
				System.out.println(socketLine);
			}
			
		} catch (NoSuchElementException e) {
			
			// vengono gestiti eventuali errori
			System.out.println("connessione chiusa");
			
		} finally {
			
			// vengono rilasciati dalla memoria tutti i processi di rete e di lettura dei dati
			stdin.close();
			socketIn.close();
			socketOut.close();
			socket.close();
		}
	}

    public static void main(String[] args) throws Exception {

        try {
        	startClient("localhost", 4567); // la funzione viene inserita all'interno di una closure try/catch per gestire gli eventuali errori
        } catch (IOException e) {
        	System.out.println("errore nell'avvio del client socket");
        }

    }
    
}

