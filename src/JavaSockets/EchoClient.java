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
    	
    	if (args.length > 0) {
    		
    		Appearence.helloHeader();
    		
	    	switch (args[0]) {
	    		
	    		case "blood":
	    			
	    		
	    		case "wet":
	    			
	    		
	    		case "connect":
	    			
	    			switch (args[1]) {
	    				
	    				case "to":
	    					
	    				
	    				case "new":
	    					
	    			}
	    		
	    		case "addressbook":
	    			
	    			AddressBook addressBook = new AddressBook("/Users/francesco/eclipse-workspace/JavaSockets/src/JavaSockets/address_book.xml");
	    			
	    			switch (args[1]) {
	    			
	    				case "list":
	    					addressBook.list();
	    					
	    				case "add":
	    					String hostName = args[2];
	    					String hostAddress = args[3];
	    					String hostPort = args[4];
	    					
	    					// TODO: controllo errori
	    					
	    					addressBook.add(new AddressBookItem(hostName, hostAddress, hostPort, "", ""));
	    					
	    					System.out.println("Il seguente elemento è stato aggiunto agli host salvati:\n");
	    					System.out.println("	Nome: " + hostName);
	    					System.out.println("	Indirizzo: " + hostAddress);
	    					System.out.println("	Porta: " + hostPort);
	    					
	    					System.out.println("\n");
	    					
	    				case "edit":
	    					
	    					
	    				case "delete":
	    					
	    			}
	    			
	    		
	    		case "listen":
	    			
	    		
	    	}
	    	
    	} else {
    		Appearence.helloHeader();
    	}

//        try {
//        	startClient("localhost", 4567); // la funzione viene inserita all'interno di una closure try/catch per gestire gli eventuali errori
//        } catch (IOException e) {
//        	System.out.println("errore nell'avvio del client socket");
//        }

    }
    
}

