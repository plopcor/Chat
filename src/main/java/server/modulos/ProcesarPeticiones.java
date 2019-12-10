package server.modulos;

import java.util.Map;

import classes.Usuario;
import classes.peticiones.Peticion;

public class ProcesarPeticiones {

	public static void procesar(Usuario usuario, Peticion peticion) {

		// Ver tipo de peticion (header => type)
		switch (peticion.getHeaders().get("type").toLowerCase()) {

		case "userdata":
			procesarUserData(usuario, peticion);
			break;

		default:
			System.err.println("No se ha podido procesar la peticion");
			System.err.println("Tipo de peticion desconocido => \""+ peticion.getHeaders().get("type") + "\"");
		}
	}
	
	
	//
	// PROCESADORES
	//
	
	private static void procesarUserData(Usuario usuario, Peticion peticion) {
		
		for (Map.Entry<String, String> par : peticion.getBody().entrySet()) {
			
			// Mirar la informacion que se quiere actualizar del usuario
			switch(par.getKey().toLowerCase().trim()) {
			
				case "name": // Canvio de nombre
					usuario.setNombre(par.getValue());
					break;
			}
		}
		
	}
	

}
