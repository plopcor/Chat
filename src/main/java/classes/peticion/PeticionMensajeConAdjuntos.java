package classes.peticion;

import classes.peticion.bodies.BodyMensajeConAdjuntos;;

public class PeticionMensajeConAdjuntos extends Peticion {

	private static final long serialVersionUID = 1L;

	// CONSTRUCTOR
	
	public PeticionMensajeConAdjuntos() {
		super();
		this.body = new BodyMensajeConAdjuntos();
	}
	
}
