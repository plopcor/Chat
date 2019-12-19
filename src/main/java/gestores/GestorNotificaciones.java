package gestores;

import classes.notificacion.Notificacion;
import classes.notificacion.NotificacionConexion;
import classes.notificacion.NotificacionDesconexion;
import classes.usuario.Usuario;
import cliente.Cliente;

public class GestorNotificaciones {

	private EventosGestor eventos;

	//
	// CONSTRUCTOR
	//

	public GestorNotificaciones() {
	}

	public GestorNotificaciones(EventosGestor eventos) {
		this.eventos = eventos;
	}

	//
	// METODOS
	//

	public void procesar(Usuario usuario, Notificacion notificacion) {

		Cliente.log("@ Recibido notificacion de \"" + usuario.getPerfil().getNombre() + "\"");

		if (notificacion instanceof NotificacionConexion)
			procesarNotificacionConexion(usuario, (NotificacionConexion) notificacion);

		else if (notificacion instanceof NotificacionDesconexion)
			procesarNotificacionDesconexion(usuario, (NotificacionDesconexion) notificacion);

		else {
			System.err.println("[INFO] No se ha podido procesar la notificacion");
			System.err.println(
					"[INFO] Tipo de notificacion desconocida => \"" + notificacion.getClass().getSimpleName() + "\"");
		}
	}

	//
	// PROCESADORES
	//

	private void procesarNotificacionConexion(Usuario usuario, NotificacionConexion notificacion) {
		// DEBUG
		Cliente.log("# RECIBIDO => Conexion");
	}

	private void procesarNotificacionDesconexion(Usuario usuario, NotificacionDesconexion notificacion) {
		// DEBUG
		Cliente.log("# RECIBIDO => Desconexion");
	}
	
	
	//
	// SETTERS & GETTERS
	//
	
	public void setEventos(EventosGestor eventos) {
		this.eventos = eventos;
	}

}
