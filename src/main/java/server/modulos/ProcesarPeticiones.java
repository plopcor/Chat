package server.modulos;

import java.util.Map;

import classes.peticion.Peticion;
import classes.peticion.cuerpo.PeticionUserData;
import classes.usuarios.Usuario;

public class ProcesarPeticiones {

	public static void procesar(Usuario usuario, Peticion peticion) {

		// Ver tipo de peticion (header => type)
		switch (peticion.getHeaders().get("type").toLowerCase()) {

		case "userdata":
			procesarUserData(usuario, (PeticionUserData) peticion);
			break;

		default:
			System.err.println("No se ha podido procesar la peticion");
			System.err.println("Tipo de peticion desconocido => \""+ peticion.getHeaders().get("type") + "\"");
		}
	}
	
	
	//
	// PROCESADORES
	//
	
	private static void procesarUserData(Usuario usuario, PeticionUserData peticion) {
		
		// METODO 1
		
		// Nombre
		if(peticion.hasNombre())
			usuario.setNombre(peticion.getNombre());
		
//		if(peticion.hasAlgo())
//			usuario.setCosa(peticion.getAlgo());
		
		
//		// METODO 2
//		// - Esta menos parametrizado, podria haber problemas en un futuro al meterle mas cosas y no actualizar los metodos
//		
//		for (Map.Entry<String, String> par : peticion.getBody().entrySet()) {
//			
//			// Mirar la informacion que se quiere actualizar del usuario
//			switch(par.getKey().toLowerCase().trim()) {
//			
//				case "name": // Canvio de nombre
//					usuario.setNombre(par.getValue());
//					break;
//			}
//		}
		
	}
	

}
