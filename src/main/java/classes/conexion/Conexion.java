package classes.conexion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import classes.peticion.Peticion;

public class Conexion implements Runnable {

	private Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;
	
	// Threads
	private Thread readThread;

	// Eventos
	private EventosConexion eventos;
	
	// CONSTRUCTOR
	public Conexion(Socket socket) {
		this.socket = socket;
		
		try {
			this.inputStream = socket.getInputStream();
			this.outputStream = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//
	// METODOS
	//
	public void start() {
		// Empezar lectura
		this.readThread = new Thread(this);
		this.readThread.start();
	}
	
	public void run() {
		
		BufferedReader in = null;
    	String data = null;
    	
    	// Cojer input stream de la conexion
		in = new BufferedReader(new InputStreamReader(inputStream));

		do {

			try {
				
				// Leer datos
				data = in.readLine();

				// Accionar evento
				if(eventos != null)
					this.eventos.onMensajeEnviado(data);

			} catch (SocketException e) {
				
				// Si el usuaro se desconecta
				if(eventos != null)
					this.eventos.onDesconectado();
				data = null;
				
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("[Error] Error al leer datos del cliente (" + this.getIP() + ")");
				data = null;
			}

		} while (data != null);
	}
	
	public void sendString(String data) {
		PrintWriter out = new PrintWriter(new OutputStreamWriter(this.getOutputStream()), true);
		out.println(data.trim());
		out.flush();
	}
	
	public void sendPeticion(Peticion peticion) {
		
		if(peticion == null) {
			System.err.println("Error: La peticion es nula, intento de envio fallido");
			return;
		}
		
		// ENVIAR PETICION SERIALIZADA
		try (ObjectOutputStream objOutStream = new ObjectOutputStream(this.getOutputStream());) {
			
			objOutStream.writeObject(peticion);
			
			System.out.println();
			
		} catch (IOException e) {
			System.err.println("Error al enviar peticion (Escriptura de objeto)");
			e.printStackTrace();
		}
	}
	
	
	// GETTERS & SETTERS
	public Socket getSocket() {
		return this.socket;
	}
	
	public InputStream getInputStream() {
		return this.inputStream;
	}
	
	public OutputStream getOutputStream() {
		return this.outputStream;
	}
	
	public String getIP() {
		return socket.getInetAddress().getHostAddress();
	}
	
	// EVENTOS
	public void setEventos(EventosConexion eventos) {
		this.eventos = eventos;
	}
}
