package classes.peticion;

import classes.peticion.bodies.BodyDatosUsuario;

public class PeticionDatosUsuario extends Peticion {

	private static final long serialVersionUID = 1L;


	// CONSTRUCTOR
	public PeticionDatosUsuario() {
		super();
		this.body = new BodyDatosUsuario();
	}
	
	
	// GETTERS & SETTERS

	@Override
	public BodyDatosUsuario getBody() {
		return (BodyDatosUsuario) this.body;
	}
	
}
