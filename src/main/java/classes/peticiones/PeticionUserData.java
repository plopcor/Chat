package classes.peticiones;

public class PeticionUserData extends Peticion {

	/*
	 * Peticion para los datos de usuario (por ejemplo: actualizar nombre)
	 */
	
	// CONSTRUCTOR
	public PeticionUserData() {
		super();
		this.headers.put("type", "userData");
	}

	// METODOS
	public void setNombre(String name) {
		this.addData("name", name.trim());
	}
	
	public boolean hasNombre() {
		return (this.getNombre() != null & this.getNombre().length() > 0);
		//return (this.body.get("name") != null && this.body.get("name").length() > 0);
	}
	
	public String getNombre() {
		return this.body.get("name");
	}
	
	
	
}
