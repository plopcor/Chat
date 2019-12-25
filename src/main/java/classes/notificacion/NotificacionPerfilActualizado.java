package classes.notificacion;

import classes.Perfil;

public class NotificacionPerfilActualizado extends Notificacion {

	private static final long serialVersionUID = 1L;

	private Perfil perfilAntiguo;
	private Perfil perfilNuevo;
	
	//
	// CONSTRUCTOR
	public NotificacionPerfilActualizado(Perfil perfilAntiguo, Perfil perfilNuevo) {
		this.perfilAntiguo = perfilAntiguo;
		this.perfilNuevo = perfilNuevo;
	}
	
	//
	// GETTERS & SETTERS
	//
	
	public Perfil getPerfilAntiguo() {
		return perfilAntiguo;
	}

	public Perfil getPerfilNuevo() {
		return perfilNuevo;
	}
	
	@Override
	public String toString() {
		
		String str = "";
		
		// Nombre cambiado ??
		if(!perfilNuevo.getNombre().equals(perfilAntiguo.getNombre()))
			str += "- Nombre (" + perfilAntiguo.getNombre() + " => " + perfilNuevo.getNombre() + ")\n";
		
		
		// Si no ha cambiado nada
		if(str.isEmpty())
			str = "Nada";
		
		return str;
	}
}
