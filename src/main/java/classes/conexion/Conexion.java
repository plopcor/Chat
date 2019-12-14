package classes.conexion;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

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
		// Empezar lectura para recibir datos
		this.readThread = new Thread(this);
		this.readThread.start();
	}
	
	public void run() {
		
		// Crear stream de objetos
		ObjectInputStream objInputStream;
		
		try {
			objInputStream = new ObjectInputStream(this.inputStream);
		} catch (IOException e) {
			System.err.println("Error al abrir el input stream para objetos");
			e.printStackTrace();
			return;
		}
		
		// Leer objetos
		
		Object objRecibido;

		try {

			objRecibido = objInputStream.readObject();
			
			// Accionar evento
			if(eventos != null)
				this.eventos.onConexionObjetoRecibido(objRecibido);
			
		} catch (ClassNotFoundException e) {
			System.err.println("Error al leer objeto serializado, clase desconocida");
			e.printStackTrace();
			
		} catch (IOException e) {
			System.err.println("Error al leer objeto serializado");
			e.printStackTrace();
		}
	}
	
	public void sendString(String data) {
		data = data.trim();
		
		if(data.isEmpty())
			return;
		
		PrintWriter out = new PrintWriter(new OutputStreamWriter(this.getOutputStream()), true);
		out.println(data);
		out.flush();
	}
	
	public void sendPeticion(Peticion peticion) {
		
		if(peticion == null) {
			System.err.println("Error: La peticion es nula, intento de envio fallido");
			return;
		}
		
		// ENVIAR PETICION SERIALIZADA
		try {
			
			ObjectOutputStream objOutStream = new ObjectOutputStream(this.getOutputStream());
			
			objOutStream.writeObject(peticion);
			
			System.out.println("[Correcto] Peticion enviada");
			
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
