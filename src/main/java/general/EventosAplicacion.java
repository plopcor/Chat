package general;

import classes.peticion.*;
import classes.usuario.Usuario;
import classes.notificacion.*;

public interface EventosAplicacion {

	// Peticiones
	void onMensaje(Usuario usuario, PeticionMensaje peticion);
	void onMensajeConAdjuntos(Usuario usuario, PeticionMensajeConAdjuntos peticion);
	void onDatosUsuario(Usuario usuario, PeticionDatosUsuario peticion);
	
	// Notificaciones
	void onNotificacionDesconexion(Usuario usuario, NotificacionDesconexion notificacion);
	void onNotificacionConexion(Usuario usuario, NotificacionConexion notificacion);
	
}
