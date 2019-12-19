package gestores;

import classes.core.ProcesarNotificaciones;
import classes.core.ProcesarPeticiones;
import classes.notificacion.Notificacion;
import classes.notificacion.NotificacionConexion;
import classes.notificacion.NotificacionDesconexion;
import classes.peticion.Peticion;
import classes.peticion.PeticionDatosUsuario;
import classes.peticion.PeticionMensaje;
import classes.peticion.PeticionMensajeConAdjuntos;
import classes.usuario.Usuario;
import general.EventosAplicacion;

public class Gestor implements EventosGestor {

	private GestorNotificaciones gestorNotificaciones;
	private GestorPeticiones gestorPeticiones;
	private EventosAplicacion eventos;
	
	// CONSTRUCTOR
	
	public Gestor() {
		
		// Crear sub-gestores y poner este objeto como event-handler 
		this.gestorNotificaciones = new GestorNotificaciones(this);
		this.gestorPeticiones = new GestorPeticiones(this);
	}
	
	public Gestor(EventosAplicacion eventos) {
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

	
	// EVENTOS DE PROCESAR
	
	@Override
	public void onProcesadoMensaje(Usuario usuario, PeticionMensaje peticion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProcesadoMensajeConAdjuntos(Usuario usuario, PeticionMensajeConAdjuntos peticion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProcesadoDatosUsuario(Usuario usuario, PeticionDatosUsuario peticion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProcesadoNotificacionDesconexion(Usuario usuario, NotificacionDesconexion notificacion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProcesadoNotificacionConexion(Usuario usuario, NotificacionConexion notificacion) {
		// TODO Auto-generated method stub
		
	}
	
}
