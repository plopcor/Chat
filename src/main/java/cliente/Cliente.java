package cliente;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import classes.Perfil;
import classes.peticion.PeticionDatosUsuario;
import classes.peticion.PeticionMensaje;
import classes.usuario.Usuario;
import gestores.BORRAR_Gestor;

public class Cliente{

	private Usuario usuario;
    private Scanner scn;
    private static boolean debugMode = false;
    private BORRAR_Gestor gestor;
    
    public Cliente (InetAddress serverAddress, int serverPort) {
        try {
        	// Crear gestor y usuario
        	gestor = new BORRAR_Gestor();
        	this.usuario = new Usuario(new Socket(serverAddress, serverPort));
        	this.scn = new Scanner(System.in);
        	
        	// Enviar eventos del usuario al gestor
        	this.usuario.setEventos(gestor);
        	
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
    
    // GETTERS & SETTERS
    public Usuario getUsuario() {
    	return this.usuario;
    }
    
	public static void log(String texto) {
		if(debugMode)
			System.out.println(texto);
	}
    
}
