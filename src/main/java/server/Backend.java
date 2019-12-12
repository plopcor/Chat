package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import classes.peticion.Peticion;
import classes.usuarios.EventosUsuario;
import classes.usuarios.Usuario;

public class Backend implements EventosUsuario {

	public static Backend backend;
    private ArrayList<Usuario> usuarios;
	
    // CONSTRUCTOR
    private Backend() {
    	usuarios = new ArrayList<Usuario>();
    }
    
    public static Backend getInstance() {
    	if(backend == null)
    		backend = new Backend();
    	return backend;
    }
    
    public void conectarUsuario(Usuario usuario) throws IOException {
    	
    	if(usuario == null)
    		throw new IOException("El cliente esta vacio");
    	
    	// Si el usuario se conecta correctamente
    	System.out.println("Cliente conectado: " + usuario.getConexion().getIP());
		
    	// Cojer eventos
    	usuario.setEventos(this);
    	
		// Empezar a leer datos
    	usuario.start();
    	
    	// Añadir a la lista
    	usuarios.add(usuario);
    }
    
    public void desconectarUsuario(Usuario usuario) {
    	if (usuarios.remove(usuario))
    		emitirMensaje(usuario.getConexion().getIP() + " desconectado");
    }

	
	public void emitirMensaje(String mensaje) {
		// Enviar mensaje a todos los usarios
		for(Usuario u : usuarios)
			u.getConexion().send(mensaje);
	}
	
	public void emitirMensajeRecibido(Usuario usuario, String mensaje) {
		// Emitir mensaje a todos menos al usuario especificado (el emisor)
		for(Usuario u : usuarios)
			if(usuario != u)
				u.getConexion().send("[" + usuario.getNombre() + "]: " + mensaje);
	}

	public void procesarMensaje(Usuario usuario, String mensaje) {

		// Si es una peticio
		if(mensaje.startsWith("{")) {
			
			// Crear peticion con el mensaje
			Peticion p = new Peticion();
			p.fromJSONString(mensaje);
			
			// Si la peticion era valida, procesarla
			if(p != null)
				procesarPeticion(usuario, p);
			else
				return;
			
		} else if (mensaje.startsWith("/")) {
			
			// Comandos
			
		} else {
			
			// Mensaje normal
			System.out.println("[" + usuario.getConexion().getIP() + "] (" + usuario.getNombre() + "): " + mensaje);
			emitirMensajeRecibido(usuario, mensaje);
		}
		
	}

	public void procesarPeticion(Usuario usuario, Peticion peticion) {
		server.modulos.ProcesarPeticiones.procesar(usuario, peticion);
	}
	
	// EVENTOS
	public void onMensajeRecibido(Usuario usuario, String mensaje) {
		procesarMensaje(usuario, mensaje);
	}
	
	public void onDesconectado(Usuario usuario) {
		System.out.println("" + usuario.getConexion().getIP() + " desconectado");
		desconectarUsuario(usuario);
	}
	
}
