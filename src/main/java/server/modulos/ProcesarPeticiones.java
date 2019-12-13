package server.modulos;

import java.util.Map;

import classes.peticion.Peticion;
import classes.peticion.PeticionDatosUsuario;
import classes.peticion.bodies.BodyMensaje;
import classes.peticion.*;
import classes.usuarios.Usuario;

public class ProcesarPeticiones {

	public static void procesar(Usuario usuario, Peticion peticion) {

		
		if(peticion instanceof PeticionDatosUsuario)
			System.out.println("RECIBIDO => Datos de usuario");
			//procesarPeticionDatos(usuario, (PeticionDatosUsuario) objRecibido);
		
		else if (peticion instanceof PeticionMensaje)
			procesarPeticionMensaje(usuario, (PeticionMensaje) peticion);
		
		else if (peticion instanceof PeticionNotificacion)
			procesarPeticionNotificacion(usuario, (PeticionNotificacion) peticion);
		
		else {
			System.err.println("No se ha podido procesar la peticion");
			System.err.println("Tipo de peticion desconocido => \""+ peticion.getClass().getSimpleName() + "\"");
		}
	}
	
	
	//
	// PROCESADORES
	//
	
	private static void procesarPeticionDatos(Usuario usuario, PeticionDatosUsuario p) {
		
		if(p == null)
			return;
		
		BodyMensaje bdMsg = p.getBody();
		
		if(bdMsg.hasMensaje()) {
			
		}
		
		
	} 
	
	
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
