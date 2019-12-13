package classes.peticion;

public class PeticionMensaje_v2 extends Peticion {

	private static final long serialVersionUID = 1L;

	String mensaje;
	
	// CONSTRUCTOR
	
	public PeticionMensaje_v2() {
		super();
	}
	
	public PeticionMensaje_v2(String mensaje) {
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
