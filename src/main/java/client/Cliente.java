package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

	private Socket socket;
    private Scanner scanner;
    
    public Cliente (InetAddress serverAddress, int serverPort) {
        try {
			this.socket = new Socket(serverAddress, serverPort);
			 this.scanner = new Scanner(System.in);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void start() {
        String input;
        
        while (true) {
        
        	input = scanner.nextLine();
            
        	PrintWriter out = null;
			try {
				out = new PrintWriter(this.socket.getOutputStream(), true);
				out.println(input);
	            out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
    
    public Socket getSocket() {
    	return this.socket;
    }
    
}
