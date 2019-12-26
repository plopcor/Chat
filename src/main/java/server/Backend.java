package server;

import java.util.ArrayList;

import classes.peticion.*;
import classes.notificacion.*;
import classes.usuario.Usuario;
import general.EventosAplicacion;
import gestores.GestorGeneral;

public class Backend implements EventosAplicacion {

	public Backend backend;
    private ArrayList<Usuario> usuarios;
    private boolean debugMode = true;
    private GestorGeneral gestor;
    
    //
    // CONSTRUCTOR
    public Backend() {
    	
    	// Crear gestor del servidor (este objeto sera el event-handler)
    	gestor = new GestorGeneral(this);
    	// Lista de usuarios
    	usuarios = new ArrayList<Usuario>();
    }
    
    //
    // METODOS
    //
    
    public void conectarUsuario(Usuario usuario) {
    	
    	if(usuario == null) {
    		System.err.println("El cliente esta vacio");
    		return;
    	}
    	
    	// Si el usuario se conecta correctamente
    	System.out.println("[INFO] Cliente conectado: " + usuario.getConexion().getIP());
		
    	// Informar de la conexion a los demas clientes
    	emitirNotificacion(new NotificacionConexion(usuario.getPerfil()));
    	
    	// Enviar eventos del usuario al gestor del servidor
    	usuario.setEventos(this.gestor);
    	
		// Empezar a leer datos
    	usuario.start();
    	
    	// Añadir a la lista
    	usuarios.add(usuario);
    }
    
    public void desconectarUsuario(Usuario usuario) {

    	if (usuarios.remove(usuario)) {
    		// Enviar notificacion a los demas clientes
    		NotificacionDesconexion notificacion = new NotificacionDesconexion(usuario.getPerfil());
    		this.emitirNotificacionRecibida(usuario, notificacion);
    	}
    }
    
    public void log(String texto) {
		if(debugMode)
			System.out.println(texto);
	}
    
    // PETICIONES
    
	public void emitirPeticion(Peticion peticion) {

		if(peticion == null)
			return;
		
		// Enviar peticion a todos los usarios
		for(Usuario u : usuarios)
			u.getConexion().sendPeticion(peticion);

	}
	
	public void emitirPeticionRecibida(Usuario usuario, Peticion peticion) {
		
		// Emitir peticion a todos menos al usuario especificado (el emisor)
		for(Usuario u : usuarios)
			if(usuario != u)
				u.getConexion().sendPeticion(peticion);	
	}
	
	// NOTIFICACIONES
	public void emitirNotificacion(Notificacion notificacion) {

		if(notificacion == null)
			return;
		
		for(Usuario u : usuarios)
			u.getConexion().sendNotificacion(notificacion);	
	}
	
	public void emitirNotificacionRecibida(Usuario usuario, Notificacion notificacion) {
		if(notificacion == null)
			return;
		
		for(Usuario u : usuarios)
			if(u != usuario)
				u.getConexion().sendNotificacion(notificacion);
	}

	//
	// GETTERS & SETTERS
	//
	
	public GestorGeneral getGestor() {
		return gestor;
	}

	//
	// EVENTOS
	//
	
	@Override
	public void onMensaje(Usuario usuario, PeticionMensaje peticion) {
		log("[MENSAJE] " + usuario.getPerfil().getNombre() + " (" + usuario.getConexion().getIP() + ") => " + peticion.getMensaje());

		// Poner emisor
		peticion.getHeader().setPerfilEmisor(usuario.getPerfil());
		
		// Re-enviar peticion a los demas clientes
		emitirPeticionRecibida(usuario, peticion);
	}

	@Override
	public void onMensajeConAdjuntos(Usuario usuario, PeticionMensajeConAdjuntos peticion) {
		log("@ [MENSAJE][ADJUNTOS] " + usuario.getPerfil().getNombre() + " (" + usuario.getConexion().getIP() + ") => " + peticion.getMensaje() + " ==> *ADJUNTOS*");		

		// Poner emisor
		peticion.getHeader().setPerfilEmisor(usuario.getPerfil());
		
		// Re-enviar peticion a los demas clientes
		emitirPeticionRecibida(usuario, peticion);
	}

	@Override
	public void onDatosUsuario(Usuario usuario, PeticionDatosUsuario peticion) {
		log("@ [DATOS USUARIO] " + usuario.getPerfil().getNombre() + " (" + usuario.getConexion().getIP() +  ") => " + peticion.getPerfil().getNombre());

		// Crear notificacion con datos antiguos y nuevos
		NotificacionPerfilActualizado notificacion = new NotificacionPerfilActualizado(usuario.getPerfil(), peticion.getPerfil());
		
		// Actualizar datos del usuario
		usuario.setPerfil(peticion.getPerfil());
		
		// Enviar notificacion a los demas clientes
		this.emitirNotificacionRecibida(usuario, notificacion);
	}

	@Override
	public void onNotificacionConexion(Usuario usuario, NotificacionConexion notificacion) {
		log("@ [CONEXION] " + usuario.getPerfil().getNombre() + " (" + usuario.getConexion().getIP() + ")");
		this.emitirNotificacionRecibida(usuario, notificacion);
	}

	@Override
	public void onNotificacionDesconexion(Usuario usuario, NotificacionDesconexion notificacion) {
		log("@ [DESCONEXION] " + usuario.getPerfil().getNombre() + " (" + usuario.getConexion().getIP() + ")");
		
		System.out.println("[INFO] " + usuario.getPerfil().getNombre() + " se ha desconectado.");
		desconectarUsuario(usuario);
	}

	@Override
	public void onNotificacionPerfilActualizado(Usuario usuario, NotificacionPerfilActualizado notificacion) {
		log("@ [ERROR] NO TENDRIA QUE RECIBIR NINGUNA // Notificacion perfil actualizado de " + usuario.getPerfil().getNombre() + " (" + usuario.getConexion().getIP() + ")");
	}

}
