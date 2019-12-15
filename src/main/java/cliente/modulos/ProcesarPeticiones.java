package cliente.modulos;

import classes.usuario.Usuario;
import cliente.Cliente;
import classes.Perfil;
import classes.peticion.*;

public class ProcesarPeticiones {

	public static void procesar(Usuario usuario, Peticion peticion) {

		Cliente.log("@ Recibido peticion de \"" + usuario.getPerfil().getNombre() + "\"");
		
		if(peticion instanceof PeticionDatosUsuario)
			procesarPeticionDatos(usuario, (PeticionDatosUsuario) peticion);
			
		else if (peticion instanceof PeticionMensaje) // Incluye "MensajeConAdjuntos"
			procesarPeticionMensaje(usuario, (PeticionMensaje) peticion);
		
		else {
			System.err.println("[INFO] No se ha podido procesar la peticion");
			System.err.println("[INFO] Tipo de peticion desconocido => \""+ peticion.getClass().getSimpleName() + "\"");
		}
	}
	
	
	//
	// PROCESADORES
	//
	
	private static void procesarPeticionDatos(Usuario usuario, PeticionDatosUsuario p) {
		// DEBUG
		Cliente.log("# RECIBIDO => Datos de usuario");
	} 
	
	private static void procesarPeticionMensaje(Usuario usuario, PeticionMensaje p) {
		
		// DEBUG
		Cliente.log("# RECIBIDO => Mensaje");
		
		if(p.getMensaje().length() == 0)
			return;
		
		// Ver emisor del mensaje 
		Perfil emisor = p.getHeader().getPerfilEmisor();
		
		// Construir mensaje para mostrarlo
		String mensaje = "";
		
		if(emisor != null)
			mensaje += "[" + emisor.getNombre() + "]: ";
		else
			mensaje += "[DESCONOCIDO]: ";

		mensaje += p.getMensaje();

		System.out.println(mensaje);
		
	}

}
