package JavaSockets;

import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.PrintWriter;

public class EchoClient {
	
	public static void startClient(String ip, Integer port) throws IOException {
		Socket socket = new Socket(ip, port);
		System.out.println("connessione stabilita");
		
		Scanner socketIn = new Scanner(socket.getInputStream());
		PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
		
		Scanner stdin = new Scanner(System.in);
		
		try {
			while (true) {
				String inputLine = stdin.nextLine();
				socketOut.println(inputLine);
				socketOut.flush();
				
				String socketLine = socketIn.nextLine();
				System.out.println(socketLine);
			}
			
		} catch (NoSuchElementException e) {
			System.out.println("connessione chiusa");
		}
		
		finally {
			stdin.close();
			socketIn.close();
			socketOut.close();
			socket.close();
		}
	}

    public static void main(String[] args) throws Exception {

        try {
        	startClient("localhost", 4567);
        } catch (IOException e) {
        	System.out.println("errore nell'avvio del client socket");
        }

    }
    
}

