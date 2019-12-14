package classes.peticion;

import classes.Perfil;

public class PeticionDatosUsuario extends Peticion {

	private static final long serialVersionUID = 1L;
	
	Perfil perfil;
	
	// CONSTRUCTOR
	public PeticionDatosUsuario() {
		super();
	}
	
	public PeticionDatosUsuario(Perfil perfil) {
		this();
		this.perfil = perfil;
	}
	
	
	// GETTERS & SETTERS
	
	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	public boolean hasPerfil() {
		return this.perfil != null;
	}

}
