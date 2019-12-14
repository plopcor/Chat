package server.modulos;

import classes.usuario.Usuario;
import server.Backend;
import classes.Perfil;
import classes.peticion.*;

public class ProcesarPeticiones {

	public static void procesar(Usuario usuario, Peticion peticion) {

		Backend.log("@ Recibido peticion de \"" + usuario.getPerfil().getNombre() + "\"");
		
		if(peticion instanceof PeticionDatosUsuario)
			procesarPeticionDatos(usuario, (PeticionDatosUsuario) peticion);
			
		else if (peticion instanceof PeticionMensaje) // Incluye "MensajeConAdjuntos"
			procesarPeticionMensaje(usuario, (PeticionMensaje) peticion);
		
		else if (peticion instanceof PeticionNotificacion)
			procesarPeticionNotificacion(usuario, (PeticionNotificacion) peticion);
		
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
		Backend.log("# RECIBIDO => Datos de usuario");
		
		if(!p.hasPerfil())
			return;		
		
		// Modificar perfil del usuario con los datos recibidos
		Perfil perfilDatos = p.getPerfil();
		Perfil perfilUsuario = usuario.getPerfil();
		
		// Cambiar nombre
		if(perfilDatos.getNombre().length() > 0)
			perfilUsuario.setNombre(perfilDatos.getNombre());
	} 
	
	private static void procesarPeticionMensaje(Usuario usuario, PeticionMensaje p) {
		
		// DEBUG
		Backend.log("# RECIBIDO => Mensaje");
		
		if(p.getMensaje().length() == 0)
			return;

		// Poner emisor del mensaje (para retransmitirlo a los demas clientes)
		p.getHeader().setPerfilEmisor(usuario.getPerfil());

		System.out.println("[" + usuario.getPerfil().getNombre() + "]: " + p.getMensaje() );
		Backend.log("@ Reenviando mensaje");
		
		// Re-enviar mensaje a todos los demas usuarios
		Backend.getInstance().emitirPeticionRecibida(usuario, p);	

		// Trigger evento de onUsuarioMensajeRecibido???
	}
	
	private static void procesarPeticionNotificacion(Usuario usuario, PeticionNotificacion p) {
		
		// DEBUG
		Backend.log("# RECIBIDO => Notificacion");
		
	}

}
