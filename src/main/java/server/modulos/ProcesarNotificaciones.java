package server.modulos;

import classes.notificacion.*;
import classes.usuario.Usuario;
import server.Backend;

public class ProcesarNotificaciones {

	public static void procesar(Usuario usuario, Notificacion notificacion) {

		Backend.log("@ Recibido notificacion de \"" + usuario.getPerfil().getNombre() + "\"");
		
		if(notificacion instanceof NotificacionConexion)
			procesarNotificacionConexion(usuario, (NotificacionConexion) notificacion);
			
		else if(notificacion instanceof NotificacionDesconexion)
		procesarNotificacionDesconexion(usuario, (NotificacionDesconexion) notificacion);
		
		else {
			System.err.println("[INFO] No se ha podido procesar la notificacion");
			System.err.println("[INFO] Tipo de notificacion desconocida => \""+ notificacion.getClass().getSimpleName() + "\"");
		}
	}
	
	//
	// PROCESADORES
	//

	private static void procesarNotificacionConexion(Usuario usuario, NotificacionConexion notificacion) {
		// DEBUG
		Backend.log("# RECIBIDO => Conexion");
	}

	private static void procesarNotificacionDesconexion(Usuario usuario, NotificacionDesconexion notificacion) {
		// DEBUG
		Backend.log("# RECIBIDO => Desconexion");
	}
	
}
