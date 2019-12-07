package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Usuario implements Runnable {

	private Socket socket;
	private Thread readThread;
	private String nombre = "Desconocido";

	public Usuario(Socket socketCliente) {

		this.socket = socketCliente;
		
		// Leer informacio que envie el cliente al principio
		
		
	}

	// METODOS
	
	public void start() {
		this.readThread = new Thread(this);
		this.readThread.start();
	}
	
//	public void stop() {
//		this.readThread.stop();
//	}
	

	public void run() {

		System.out.println("THREAD EMPEZADO");
		
		BufferedReader in = null;
    	String data = null;
		
		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		do {

			try {
				
				data = in.readLine();
				System.out.println("[Mensaje] " + this.getIP() + ": " + data);

			} catch (IOException e) {
				System.err.println("[Error] " + this.getIP() + " => Error al leer datos del cliente");
				e.printStackTrace();
				data = null;
			}

		} while (data != null);
	}

	public String getIP() {
		return socket.getInetAddress().getHostAddress();
	}
	
}
