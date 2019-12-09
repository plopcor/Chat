package server;

import java.io.IOException;
import java.util.ArrayList;

import classes.EventosUsuario;
import classes.Usuario;

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
				u.getConexion().send(mensaje);
	}

	public void procesarMensaje(Usuario usuario, String mensaje) {

		// Si es una peticio
		if(mensaje.startsWith("{")) {
			
			
			
		}
		
		// Si empieza con @INFO, es datos sobre el usuario
		if(mensaje.startsWith("@INFO")) {
			
			// Datos del usuario
			
			// Quitar identificador de tipo de mensaje
			mensaje = mensaje.replace("@INFO", "").trim();

			if(mensaje.contains(":")) {
				String[] msg = mensaje.split(":");
				
				// Ver que informacion se quiere poner
				switch(msg[0]) {
					case "name":
						usuario.setNombre(msg[1]);
						break;
				}
			}
			
		} else if (mensaje.startsWith("/")) {
			
			// Comandos
			
		} else {
			
			// Mensaje normal
			System.out.println("[" + usuario.getConexion().getIP() + "] (" + usuario.getNombre() + "): " + mensaje);
			emitirMensajeRecibido(usuario, mensaje);
		}
		
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
