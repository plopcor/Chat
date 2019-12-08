package classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class Conexion implements Runnable {

	private Socket socket;
	private InputStreamReader inputReader;
	private OutputStreamWriter outputWriter;
	
	// Threads
	private Thread readThread;

	// Eventos
	private EventosConexion eventos;
	
	// CONSTRUCTOR
	public Conexion(Socket socket) {
		this.socket = socket;
		
		try {
			this.inputReader = new InputStreamReader(socket.getInputStream());
			this.outputWriter = new OutputStreamWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//
	// METODOS
	//
	public void start() {
		this.readThread = new Thread(this);
		this.readThread.start();
	}
	
	public void run() {
		
		BufferedReader in = null;
    	String data = null;
    	
    	// Cojer input stream de la conexion
		in = new BufferedReader(this.inputReader);

		do {

			try {
				
				data = in.readLine();

				// Accionar evento
				if(eventos != null)
					this.eventos.onMensajeEnviado(data);
				//Backend.mensajeRecibido(this, data);

			} catch (SocketException e) {
				
				// Si el usuaro se desconecta
				if(eventos != null)
					this.eventos.onDesconectado();
				//Backend.desconectarUsuario(this);
				data = null;
				
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("[Error] " + this.getIP() + " => Error al leer datos del cliente");
				data = null;
			}

		} while (data != null);
	}
	
	public void send(String data) {
		PrintWriter out = new PrintWriter(this.getOutputWriter(), true);
		out.println(data);
		out.flush();
	}
	
	
	// GETTERS & SETTERS
	public Socket getSocket() {
		return this.socket;
	}
	
	public InputStreamReader getInputReader() {
		return this.inputReader;
	}
	
	public OutputStreamWriter getOutputWriter() {
		return this.outputWriter;
	}
	
	public String getIP() {
		return socket.getInetAddress().getHostAddress();
	}
	
	// EVENTOS
	public void setEventos(EventosConexion eventos) {
		this.eventos = eventos;
	}
}