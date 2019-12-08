package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import classes.Conexion;
import classes.EventosConexion;
import classes.EventosUsuario;
import classes.Usuario;

public class Cliente implements EventosUsuario {

	private Usuario usuario;
    private Scanner scn;
    
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
    	// Empezar lectura
    	this.usuario.start();
    	
    	String input;
    	PrintWriter out = new PrintWriter(this.getUsuario().getConexion().getOutputWriter(), true);

		// Enviar datos
        while (true) {

        	input = scn.nextLine();
            
        	out.println(input);
	        out.flush();
        }
    	
    }
    
    public void enviarDatosUsuario() {
    	
    	// Enviar datos del cliente
    	System.out.println("Nombre de usuario:");
    	String nombre = scn.nextLine();
    	
    	if(!nombre.isEmpty())
    		this.usuario.setNombre(nombre);
    	
    	// Enviar datos del cliente
		this.usuario.getConexion().send("@INFO name:" + nombre);
    	
    }
    
    // GETTERS & SETTERS
    public Usuario getUsuario() {
    	return this.usuario;
    }

    
    // EVENTOS
    
	public void onMensajeRecibido(Usuario usuario, String mensaje) {
		System.out.println("[Mensaje] "+ usuario.getConexion().getIP() + ": " + mensaje);
	}

	public void onDesconectado(Usuario usuario) {
		System.out.println("[Desconectado] " + usuario.getConexion().getIP());
	}
    
}
