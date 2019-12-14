package classes.peticion;

import java.util.ArrayList;

import classes.Fichero;

public class PeticionMensajeConAdjuntos extends PeticionMensaje {

	private static final long serialVersionUID = 1L;

	ArrayList<Fichero> ficheros;
	
	// CONSTRUCTOR
	
	public PeticionMensajeConAdjuntos() {
		super();
	}
	
	public PeticionMensajeConAdjuntos(ArrayList<Fichero> ficheros) {
		this();
		this.ficheros = ficheros;
	}
	
	
	public boolean añadirFichero(Fichero fichero) {
		return this.ficheros.add(fichero);
	}	

	public boolean eliminarFichero(Fichero fichero) {
		return this.ficheros.remove(fichero);
	}
	
	public boolean contieneFichero(Fichero fichero) {
		return this.ficheros.contains(fichero);
	}
	
	// GETTERS & SETTERS

	public ArrayList<Fichero> getFicheros() {
		return ficheros;
	}

	public void setFicheros(ArrayList<Fichero> ficheros) {
		this.ficheros = ficheros;
	}
	
	public boolean hasFicheros() {
		return this.ficheros.size() > 0;
	}
}
