package gestores;

import classes.notificacion.NotificacionConexion;
import classes.notificacion.NotificacionDesconexion;
import classes.peticion.PeticionDatosUsuario;
import classes.peticion.PeticionMensaje;
import classes.peticion.PeticionMensajeConAdjuntos;
import classes.usuario.Usuario;

public interface EventosGestor {

	// Peticiones
	void onProcesadoMensaje(Usuario usuario, PeticionMensaje peticion);
	void onProcesadoMensajeConAdjuntos(Usuario usuario, PeticionMensajeConAdjuntos peticion);
	void onProcesadoDatosUsuario(Usuario usuario, PeticionDatosUsuario peticion);
	
	// Notificaciones
	void onProcesadoNotificacionDesconexion(Usuario usuario, NotificacionDesconexion notificacion);
	void onProcesadoNotificacionConexion(Usuario usuario, NotificacionConexion notificacion);
	
}
