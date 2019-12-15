package classes.notificacion;

import classes.Perfil;

/*
 * 	Notificar que un usuario se ha desconectado
 */

public class NotificacionDesconexion extends Notificacion {

	private static final long serialVersionUID = 1L;
	private Perfil perfilUsuario;
	
	
	// CONSTRUCTOR

	public NotificacionDesconexion(Perfil perfilUsuario) {
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
