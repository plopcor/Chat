package classes.peticion.bodies;

public class BodyDatosUsuario extends Body {

	String nombre;
	
	
	// CONSTRUCTOR
	
	public BodyDatosUsuario() {
		this.nombre = "Anonimo";
	}
	
	public BodyDatosUsuario(String nombre) {
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
