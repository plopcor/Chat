package classes.peticion.bodies;

public class BodyMensaje extends Body {

	String mensaje;

	
	// CONSTRUCTOR
	
	public BodyMensaje() {
		super();
	}

	public BodyMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	// GETTERS & SETTERS
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
