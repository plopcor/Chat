package classes.peticion;

import java.io.Serializable;

import classes.peticion.headers.Header;

public abstract class Peticion implements Serializable {

	private static final long serialVersionUID = 1L;
	protected Header header;
	
	// CONSTRUCTOR
	public Peticion() {
		header = new Header();
	}
	
	// METODOS
	
	
	// GETTERS & SETTERS
	
	public Header getHeader() {
		return this.header;
	}
	
}
