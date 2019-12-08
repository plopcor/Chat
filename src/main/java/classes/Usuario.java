package classes;

import java.net.Socket;

public class Usuario implements EventosConexion {

	private Conexion conexion;
	private EventosUsuario eventos;
	
	public Usuario(Socket socketCliente) {

		this.conexion = new Conexion(socketCliente);
		this.conexion.setEventos(this);
		// Leer informacio que envie el cliente al principio		
	}

	// METODOS
	public void start() {
		this.conexion.start();
	}

	public void enviarMensaje(String data) {
		this.conexion.send(data);
	}
	
	// GETTERS & SETTERS
	public Conexion getConexion() {
		return this.conexion;
	}

	public void setEventos(EventosUsuario eventos) {
		this.eventos = eventos;
	}
	
	// EVENTS
	public void onMensajeEnviado(String mensaje) {
		if(eventos != null)
			this.eventos.onMensajeRecibido(this, mensaje);
	}

	public void onDesconectado() {
		if(eventos != null)
			this.eventos.onDesconectado(this);
	}
	
}
