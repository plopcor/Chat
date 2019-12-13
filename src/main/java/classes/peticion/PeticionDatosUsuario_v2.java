package classes.peticion;

public class PeticionDatosUsuario_v2 extends Peticion {

	private static final long serialVersionUID = 1L;

	String nombre;
	
	// CONSTRUCTOR
	public PeticionDatosUsuario_v2() {
		super();
	}
	
	public PeticionDatosUsuario_v2(String nombre) {
		this();
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
