package classes.peticion;

import classes.peticion.bodies.BodyMensaje;

public class PeticionMensaje extends Peticion {

	private static final long serialVersionUID = 1L;

	// CONSTRUCTOR
	
	public PeticionMensaje() {
		super();
		this.body = new BodyMensaje();
	}
	
	public PeticionMensaje(String mensaje) {
		super();
		this.body = new BodyMensaje(mensaje);
	}
	
	
	// GETTERS & SETTERS

	@Override
	public BodyMensaje getBody() {
		return (BodyMensaje) this.body;
	}
	
}
