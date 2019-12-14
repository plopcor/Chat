package classes.peticion.headers;

import java.io.Serializable;

import classes.Perfil;

public class Header implements Serializable {

	private static final long serialVersionUID = 1L;

	Perfil perfilEmisor;

	// CONSTRUCTOR
	public Header() {
		
	}
	
	public Header(Perfil perfilEmisor) {
		super();
		this.perfilEmisor = perfilEmisor;
	}

	
	// GETTERS & SETTERS
	
	public Perfil getPerfilEmisor() {
		return perfilEmisor;
	}

	public void setPerfilEmisor(Perfil perfilEmisor) {
		this.perfilEmisor = perfilEmisor;
	}
	
	public boolean hasPerfilEmisor() {
		return this.perfilEmisor != null;
	}
}
