package gestores;

import classes.notificacion.*;
import classes.peticion.*;
import classes.usuario.EventosUsuario;
import classes.usuario.Usuario;
import general.EventosAplicacion;

public class GestorCliente implements EventosUsuario, EventosSubGestorPeticiones, EventosSubGestorNotificaciones {

	private SubGestorNotificaciones gestorNotificaciones;
	private SubGestorPeticiones gestorPeticiones;
	private EventosAplicacion eventos;
	
	// CONSTRUCTOR
	
	public GestorCliente() {
		
		// Crear sub-gestores y poner este objeto como event-handler 
		this.gestorNotificaciones = new SubGestorNotificaciones(this);
		this.gestorPeticiones = new SubGestorPeticiones(this);
	}
	
	public GestorCliente(EventosAplicacion eventos) {
		this();
		this.eventos = eventos;
	}
	
	public void procesar(Usuario usuario, Object objRecibido) {

		if(objRecibido == null)
			return;
		
		// Ver tipo de objeto
		if(objRecibido instanceof Peticion)
			this.gestorPeticiones.procesar(usuario, (Peticion) objRecibido);

		else if (objRecibido instanceof Notificacion)
			this.gestorNotificaciones.procesar(usuario, (Notificacion) objRecibido);
		
		else
			System.out.println("No se puede procesar el objeto recibido, tipo de objeto desconocido");
	}

	
	// EVENTOS DE ENTRADA
	
	@Override
	public void onUsuarioObjetoRecibido(Usuario usuario, Object objRecibido) {
		procesar(usuario, objRecibido);		
	}

	@Override
	public void onUsuarioDesconectado(Usuario usuario) {
		
	}
	
	// EVENTOS DE PROCESAR / SALIDA
	
	@Override
	public void onProcesadoMensaje(Usuario usuario, PeticionMensaje peticion) {
		if(eventos != null)
			eventos.onMensaje(usuario, peticion);
	}

	@Override
	public void onProcesadoMensajeConAdjuntos(Usuario usuario, PeticionMensajeConAdjuntos peticion) {
		if(eventos != null)
			eventos.onMensajeConAdjuntos(usuario, peticion);
	}

	@Override
	public void onProcesadoDatosUsuario(Usuario usuario, PeticionDatosUsuario peticion) {
		if(eventos != null)
			eventos.onDatosUsuario(usuario, peticion);
	}

	@Override
	public void onProcesadoNotificacionConexion(Usuario usuario, NotificacionConexion notificacion) {
		if(eventos != null)
			eventos.onNotificacionConexion(usuario, notificacion);
	}
	
	@Override
	public void onProcesadoNotificacionDesconexion(Usuario usuario, NotificacionDesconexion notificacion) {
		if(eventos != null)
			eventos.onNotificacionDesconexion(usuario, notificacion);
	}

	@Override
	public void onProcesadoNotificacionPerfilActualizado(Usuario usuario, NotificacionPerfilActualizado notificacion) {
		if(eventos != null)
			eventos.onNotificacionPerfilActualizado(usuario, notificacion);		
	}
	
}
