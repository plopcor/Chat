package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import classes.usuario.Usuario;

public class Servidor {
	
    private ServerSocket server;
    private Backend backend;
    
    public Servidor(String ipAddress, int port) {
    	
    	try {
    		if (ipAddress != null && !ipAddress.isEmpty()) 
    			this.server = new ServerSocket(port, 1, InetAddress.getByName(ipAddress));
    		else
    			this.server = new ServerSocket(port, 1, InetAddress.getLocalHost());
    		
    	} catch (Exception e) {
    		System.err.println("Error al crear el servidor " + e.getMessage());
    	}
    	
    	backend = Backend.getInstance();
    }
    
    
    public void listen() {
    	
        Socket cliente = null;
        
        do {
        
			try {
				
				// Aceptar conexion del cliente
				cliente = this.server.accept();
				
				// Crear nuevo cliente conectado
				Usuario usuarioConectado = new Usuario(cliente);
				
				// Añadir a la lista i empezar a leer datos
				backend.conectarUsuario(usuarioConectado);
				
			} catch (IOException e) {
				System.err.println("Error al aceptar conexion de un cliente");
				e.printStackTrace();
			}
			
        } while (true);
    }
    
    
    // GETTERS & SETTERS
    
    public InetAddress getSocketAddress() {
        return this.server.getInetAddress();
    }
    
    public int getPort() {
        return this.server.getLocalPort();
    }
    
    
}