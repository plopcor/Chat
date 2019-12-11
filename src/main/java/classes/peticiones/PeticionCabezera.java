package classes.peticiones;

public class PeticionCabezera {

	protected TipoPeticion tipoPeticion;
	
	// CONSTRUCTOR
	public PeticionCabezera() {
		this.tipoPeticion = TipoPeticion.DESCONOCIDA;
	}
	
	
	// GETTERS & SETTERS
	public void setTipoPeticion(TipoPeticion tipoPeticion) {
		this.tipoPeticion = tipoPeticion;
	}
	
	public TipoPeticion getTipoPeticion() {
		return this.tipoPeticion;
	}
	
}
