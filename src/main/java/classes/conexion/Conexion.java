package classes.conexion;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import classes.notificacion.Notificacion;
import classes.peticion.Peticion;

public class Conexion implements Runnable {

	private Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;
	
	private ObjectInputStream objInputStream;
	private ObjectOutputStream objOutStream;
	
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
			
			this.objOutStream = new ObjectOutputStream(socket.getOutputStream());
			this.objInputStream = new ObjectInputStream(socket.getInputStream());
			

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
		
		Object objRecibido;
		boolean salir = false;
		
		// Leer objetos
		do {
		
			try {
	
				objRecibido = objInputStream.readObject();
				
				// Accionar evento
				if(eventos != null && objRecibido != null)
					this.eventos.onConexionObjetoRecibido(objRecibido);
				
			} catch (ClassNotFoundException e) {
				System.err.println("Error al leer objeto serializado, clase desconocida");
				e.printStackTrace();
		
			} catch (SocketException | EOFException e) {
				
				// Si se pierde la conexion, enviar evento
				salir = true;
				
				// Trigger evento
				if(this.eventos != null)
					this.eventos.onDesconectado();
				
			} catch (IOException e) {
				System.err.println("Error al leer objeto serializado");
				e.printStackTrace();
				salir = true;
			}
		
		} while (!salir);
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
		
		sendObjeto(peticion);
	}
	
	public void sendNotificacion(Notificacion notificacion) {
		if(notificacion == null) {
			System.err.println("Error: La notificacion es nula, intento de envio fallido");
			return;
		}
		
		sendObjeto(notificacion);
	}
	
	// Enviar objetos serializados
	private void sendObjeto(Object objeto) {
		try {

			objOutStream.writeObject(objeto);
			objOutStream.flush();
//			System.out.println("[Correcto] Peticion enviada");

		} catch (IOException e) {
			System.err.println("Error al escribir objeto de tipo \"" + objeto.getClass().getSimpleName()+ "\". " + e.getMessage());
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
