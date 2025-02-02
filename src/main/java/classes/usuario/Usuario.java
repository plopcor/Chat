package classes.usuario;

import javax.net.ssl.SSLSocket;

import classes.Perfil;
import classes.conexion.Conexion;
import classes.conexion.EventosConexion;

public class Usuario implements EventosConexion {

	private Perfil perfil;
	private Conexion conexion;
	private EventosUsuario eventos;
	
	public Usuario(SSLSocket socketCliente) {

		this.perfil = new Perfil("Anonimo");
		this.conexion = new Conexion(socketCliente);
		this.conexion.setEventos(this);
		
	}

	// METODOS
	public void start() {
		this.conexion.start();
	}
	
	// GETTERS & SETTERS
	public Perfil getPerfil() {
		return this.perfil;
	}
	
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	public Conexion getConexion() {
		return this.conexion;
	}
	
	public void setEventos(EventosUsuario eventos) {
		this.eventos = eventos;
	}
	
	
	// EVENTOS
	
	public void onConexionObjetoRecibido(Object objRecibido) {
		if(eventos != null)
			this.eventos.onUsuarioObjetoRecibido(this, objRecibido);
	}
	
	public void onDesconectado() {
		if(eventos != null)
			this.eventos.onUsuarioDesconectado(this);
	}
	
}
