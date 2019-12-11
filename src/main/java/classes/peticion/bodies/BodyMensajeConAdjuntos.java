package classes.peticion.bodies;

import java.util.ArrayList;

import classes.peticion.clases.Fichero;

public class BodyMensajeConAdjuntos extends BodyMensaje {

	private ArrayList<Fichero> ficheros = new ArrayList<Fichero>();

	// CONSTRUCTOR
	
	public BodyMensajeConAdjuntos() {
		super();
	}
	
	public BodyMensajeConAdjuntos(ArrayList<Fichero> ficheros) {
		super();
		this.ficheros = ficheros;
	}
	
	// METODOS
	
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
		return !this.ficheros.isEmpty();
	}
	
	
	
}
