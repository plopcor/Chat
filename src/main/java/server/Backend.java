package server;

import java.util.ArrayList;

import classes.peticion.Peticion;
import classes.peticion.PeticionMensaje;
import classes.usuario.EventosUsuario;
import classes.usuario.Usuario;

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
    
    // METODOS
    
    public void conectarUsuario(Usuario usuario) throws NullPointerException {
    	
    	if(usuario == null)
    		throw new NullPointerException ("El cliente esta vacio");
    	
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
    	System.out.println("@ " + usuario.getPerfil().getNombre() + " se ha desconectado.");
    	if (usuarios.remove(usuario))
    		emitirPeticion(new PeticionMensaje(usuario.getConexion().getIP() + " desconectado"));
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
				//u.getConexion().sendString("[" + usuario.getNombre() + "]: " + mensaje);		
	}
	
	public void procesar(Usuario usuario, Object objRecibido) {

		if(objRecibido == null)
			return;
		
		// Ver tipo de objeto
		if(objRecibido instanceof Peticion)
			server.modulos.ProcesarPeticiones.procesar(usuario, (Peticion) objRecibido);

		else
			System.out.println("No se puede procesar el objeto recibido, tipo de objeto desconocido");
	}
	
	// EVENTOS
	public void onUsuarioObjetoRecibido(Usuario usuario, Object objRecibido) {
		System.out.println("@ Objeto recibido de " + usuario.getPerfil().getNombre() + " => " + objRecibido.getClass().getSimpleName());
		procesar(usuario, objRecibido);
	}
	
	public void onUsuarioDesconectado(Usuario usuario) {
		desconectarUsuario(usuario);
	}
	
	// ON EVENTOS
	
}
