package cliente;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import classes.Perfil;
import classes.peticion.Peticion;
import classes.peticion.PeticionDatosUsuario;
import classes.peticion.PeticionMensaje;
import classes.usuario.EventosUsuario;
import classes.usuario.Usuario;

public class Cliente implements EventosUsuario {

	private Usuario usuario;
    private Scanner scn;
    private static boolean debugMode = false;
    
    public Cliente (InetAddress serverAddress, int serverPort) {
        try {
        	
        	this.usuario = new Usuario(new Socket(serverAddress, serverPort));
        	this.scn = new Scanner(System.in);
        	this.usuario.setEventos(this);
        	
        	enviarDatosUsuario();
        	
		} catch (IOException e) {
			e.printStackTrace();
		}       
    }
    
    public void start() {
    	
    	// Empezar lectura para datos recibidos
    	this.usuario.start();
    	
    	String input;
    	PeticionMensaje petMsg;

		// Empezar lectura para enviar datos
        while (true) {

        	input = scn.nextLine().trim();
        	
        	// Crear PeticioMensaje con el mensaje
        	if(!input.isEmpty()) {
        		petMsg = new PeticionMensaje(input);
        	
        		// Enviar
        		this.getUsuario().getConexion().sendPeticion(petMsg);
        	}
        }
    	
    }
    
    public void enviarDatosUsuario() {
    	
    	// Enviar datos del cliente
    	String nombre;
    	
    	do {
    		
        	System.out.println("# Nombre de usuario (30 caracteres max):");
    		nombre = scn.nextLine().trim();
    		
    	} while (nombre.isEmpty() || nombre.length() > 30);
    	
    	// Crear perfil del usuario
    	this.usuario.setPerfil(new Perfil(nombre));
    	
    	// Crear peticion con la informacion del usuario
    	PeticionDatosUsuario pDatos = new PeticionDatosUsuario(this.usuario.getPerfil());
    	
    	// Enviar peticion al servidor para que nos asigne esos datos (para los demas clientes)
    	this.usuario.getConexion().sendPeticion(pDatos);
    	   	
    }
    
    public void procesar(Usuario usuario, Object objRecibido) {
    	
    	classes.core.Procesar.procesar(usuario, objRecibido);
    	
//		if(objRecibido == null)
//			return;
//		
//		// Ver tipo de objeto
//		if(objRecibido instanceof Peticion)
//			cliente.modulos.ProcesarPeticiones.procesar(usuario, (Peticion) objRecibido);
//
//		else
//			System.out.println("No se puede procesar el objeto recibido, tipo de objeto desconocido");
	}
    
    // GETTERS & SETTERS
    public Usuario getUsuario() {
    	return this.usuario;
    }

    
    // EVENTOS

	@Override
	public void onUsuarioObjetoRecibido(Usuario usuario, Object objRecibido) {
		
		System.out.println("Objeto recibido");
		procesar(usuario, objRecibido);
		
	}

	@Override
	public void onUsuarioDesconectado(Usuario usuario) {
		System.out.println("Te has desconectado");
	}
    
	public static void log(String texto) {
		if(debugMode)
			System.out.println(texto);
	}
	
//	public void onMensajeRecibido(Usuario usuario, Object objRecibido) {
//		System.out.println("Objeto recibido");
//		
////		System.out.println("[Mensaje] "+ usuario.getConexion().getIP() + ": " + mensaje);
//	}

//	public void onDesconectado(Usuario usuario) {
//		System.out.println("[Desconectado] " + usuario.getConexion().getIP());
//	}
    
}
