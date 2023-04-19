package JavaSockets;

import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.PrintWriter;

public class EchoClient {
	
	public static void startClient(String ip, String port) throws IOException {
		
		Socket socket = new Socket(ip, Integer.parseInt(port)); // inizializzazione del socket
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
    	
    	AddressBook addressBook = new AddressBook("/Users/francesco/eclipse-workspace/JavaSockets/src/JavaSockets/address_book.xml");
    	
    	if (args.length > 0) {
    		
	    	switch (args[0]) {
	    		
	    		case "blood":
	    			Appearence.bloodyHelloHeader();
	    			break;
	    		
	    		case "wet":
	    			Appearence.wetHelloHeader();
	    			break;
	    		
	    		case "connect":
	    			
	    			switch (args[1]) {
	    				
	    				case "to":
	    					AddressBookItem itemToConnect = addressBook.itemFromString(args[2]);
	    					
	    					startClient(itemToConnect.getHostAddress(), itemToConnect.getHostPort());
	    					break;
	    					
	    				
	    				case "new":
	    					startClient(args[2], args[3]);
	    					break;
	    			}
	    			
	    			break;
	    		
	    		case "addressbook":
	    			
	    			Appearence.helloHeader();
	    			
	    			switch (args[1]) {
	    			
	    				case "list":
	    					addressBook.list();
	    					break;
	    					
	    				case "add":
	    					
	    					// TODO: far fare questo controllo a una funzione apposita in un try catch if checkArgs(2, 4, true, true) (from, to, exist?, valid?)
	    					
	    					String hostName = args[2];
	    					String hostAddress = args[3];
	    					String hostPort = args[4];
	    					
	    					// TODO: controllo errori
	    					
	    					addressBook.add(new AddressBookItem(hostName, hostAddress, hostPort, "", ""));
	    					
	    					// TODO: far fare questa cosa al logger 
	    					
	    					System.out.println("Il seguente elemento è stato aggiunto agli host salvati:\n");
	    					System.out.println("	Nome: " + hostName);
	    					System.out.println("	Indirizzo: " + hostAddress);
	    					System.out.println("	Porta: " + hostPort);
	    					
	    					System.out.println("\n");
	    					break;
	    					
	    				case "delete":
	    					addressBook.delete(args[2]);
	    					break;
	    					
	    			}
	    			
	    			break;
	    	}
	    	
    	} else {
    		
    		Appearence.helloHeader();
    		System.out.println("Non è stato specificato nessun comando, per connettersi a un host usare la seguente sintassi:\n\n	/java/sockets/path connect to new [indirizzo] [porta]");
    	}

    }
    
}

