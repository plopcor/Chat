package classes.peticiones;

public class PeticionUserData extends Peticion {

	/*
	 * Peticion para los datos de usuario (por ejemplo: actualizar nombre)
	 */
	
	// CONSTRUCTOR
	public PeticionUserData() {
		super();
		jsonHeader.put("type", "userData");
	}

	// METODOS
	public void setName(String name) {
		this.addData("name", name.trim());
	}
	
	

}
