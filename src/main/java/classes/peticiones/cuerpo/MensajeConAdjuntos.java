package classes.peticiones.cuerpo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class MensajeConAdjuntos extends Mensaje {

	private HashMap<File, byte[]> ficherosAdjuntos;
	
	// CONSTRUCTOR
	
	public MensajeConAdjuntos() {
		this.ficherosAdjuntos = new HashMap<File, byte[]>();
	}
	
	// GETTERS & SETTERS
	
	public void setFicherosAdjuntos(HashMap<File, byte[]> ficherosAdjuntos) {
		this.ficherosAdjuntos = ficherosAdjuntos;
	}
	
	public HashMap<File, byte[]> getFicherosAdjuntos() {
		return this.ficherosAdjuntos;
	}
	
	public void a�adirFichero(File archivo, byte[] bytesArchivo) {
		this.ficherosAdjuntos.put(archivo,  bytesArchivo);
	}
	
	//Llamada => a�adirFichero(fichero1, File.readAllBytes(fichero1.toPath()))
	
}
