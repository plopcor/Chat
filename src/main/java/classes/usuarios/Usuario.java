package classes.usuarios;

import java.net.Socket;

import classes.conexion.Conexion;
import classes.conexion.EventosConexion;

public class Usuario implements EventosConexion {

	private String nombre;
	private Conexion conexion;
	private EventosUsuario eventos;
	
	public Usuario(Socket socketCliente) {

		this.nombre = "Anonimo";
		this.conexion = new Conexion(socketCliente);
		this.conexion.setEventos(this);
		
	}

	// METODOS
	public void start() {
		this.conexion.start();
	}

	public void enviarMensaje(String data) {
		this.conexion.send(data);
	}
	
	// GETTERS & SETTERS
	public String getNombre() {
		return this.nombre;
	}
	
	public void setNombre(String nombre) {
		if(nombre.length() > 35)
			nombre = nombre.substring(0, 35);
		else if (nombre.length() == 0)
			nombre = "Anonimo";
		this.nombre = nombre;
	}
	
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