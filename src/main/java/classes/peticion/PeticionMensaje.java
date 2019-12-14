package classes.peticion;

public class PeticionMensaje extends Peticion {

	private static final long serialVersionUID = 1L;

	String mensaje;
	
	// CONSTRUCTOR
	
	public PeticionMensaje() {
		super();
	}
	
	public PeticionMensaje(String mensaje) {
		super();
		this.mensaje = mensaje;
	}
	

	// GETTERS & SETTERS
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public boolean hasMensaje() {
		return this.mensaje.length() > 0;
	}
	
}
