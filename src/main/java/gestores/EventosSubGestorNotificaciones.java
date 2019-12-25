package gestores;

import classes.notificacion.*;
import classes.usuario.Usuario;

public interface EventosSubGestorNotificaciones {

	// Notificaciones
	void onProcesadoNotificacionConexion(Usuario usuario, NotificacionConexion notificacion);
	void onProcesadoNotificacionDesconexion(Usuario usuario, NotificacionDesconexion notificacion);
	void onProcesadoNotificacionPerfilActualizado(Usuario usuario, NotificacionPerfilActualizado notificacion);
}
