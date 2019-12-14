package classes;

import java.io.Serializable;

public class Perfil implements Serializable {

	private static final long serialVersionUID = 1L;
	
	String nombre;
	
	// CONSTRUCTOR
	public Perfil() {
		this.nombre = "Anonimo";
	}
	
	public Perfil(String nombre) {
		this.nombre = nombre;
	}

	
	// GETTERS & SETTERS
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
