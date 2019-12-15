package classes.notificacion;

import classes.Perfil;

/*
 * Notificar que un usuario se ha conectado
 */

public class NotificacionConexion extends Notificacion {

	private static final long serialVersionUID = 1L;
	private Perfil perfilUsuario;
	
	// CONSTRUCTOR

	public NotificacionConexion(Perfil perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}

	
	// GETTERS & SETTERS

	public Perfil getPerfilUsuario() {
		return perfilUsuario;
	}

	public void setPerfilUsuario(Perfil perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}
	
}
