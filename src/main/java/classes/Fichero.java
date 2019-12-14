package classes;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;

public class Fichero implements Serializable {

	private static final long serialVersionUID = 1L;
	File fichero;
	byte[] bytesFichero;
	
	
	// CONSTRUCTOR
	
	public Fichero() {
		
	}
	
	public Fichero(File fichero) {
		
		this.fichero = fichero;
		if(fichero.exists())
			try {
				this.bytesFichero = Files.readAllBytes(fichero.toPath());
			} catch (IOException e) {
				System.err.println("Error al leer el fichero " + fichero.toPath());
			}
	}
	
	// GETTERS & SETTERS

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}
	
	public boolean hasFichero() {
		return this.fichero != null;
	}

	public byte[] getBytesFichero() {
		return bytesFichero;
	}

	public void setBytesFichero(byte[] bytesFichero) {
		this.bytesFichero = bytesFichero;
	}
	
	public boolean hasBytesFichero() {
		return this.bytesFichero.length > 0;
	}
	
}
