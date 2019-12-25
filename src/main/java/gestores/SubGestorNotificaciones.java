package gestores;

import classes.notificacion.Notificacion;
import classes.notificacion.NotificacionConexion;
import classes.notificacion.NotificacionDesconexion;
import classes.usuario.Usuario;
import cliente.Cliente;

public class SubGestorNotificaciones {

	private EventosSubGestorNotificaciones eventos;

	//
	// CONSTRUCTOR
	//

	private SubGestorNotificaciones() {
		super();
	}

	public SubGestorNotificaciones(EventosSubGestorNotificaciones eventos) {
		this();
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
		if(eventos != null)
			eventos.onProcesadoNotificacionConexion(usuario, notificacion);
	}

	private void procesarNotificacionDesconexion(Usuario usuario, NotificacionDesconexion notificacion) {
		if(eventos != null)
			eventos.onProcesadoNotificacionDesconexion(usuario, notificacion);
	}
	
	
	//
	// SETTERS & GETTERS
	//
	
	public void setEventos(EventosSubGestorNotificaciones eventos) {
		this.eventos = eventos;
	}

}
