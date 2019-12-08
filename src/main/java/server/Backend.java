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
    	System.out.println("Usuario en lista ?? " + (usuarios.contains(usuario) ? "SI" : "NO"));
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
			else
				u.getConexion().send("Eres el emisor");
	}


	// EVENTOS
	public void onMensajeRecibido(Usuario usuario, String mensaje) {
		System.out.println("[" + usuario.getConexion().getIP() + "]: " + mensaje);
		emitirMensaje(mensaje);
		//emitirMensajeRecibido(usuario, mensaje);
	}
	
	public void onDesconectado(Usuario usuario) {
		System.out.println("[" + usuario.getConexion().getIP() + "] desconectado");
		desconectarUsuario(usuario);
	}
	
	//public void emitirMensaje(String mensaje, ArrayList<Usuario> usuariosExcluidos)
	
}
