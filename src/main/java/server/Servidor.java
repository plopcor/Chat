package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {
	
    private ServerSocket server;
    private ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
    
    public Servidor(String ipAddress, int port) {
    	
    	try {
    		if (ipAddress != null && !ipAddress.isEmpty()) 
    			this.server = new ServerSocket(port, 1, InetAddress.getByName(ipAddress));
    		else
    			this.server = new ServerSocket(port, 1, InetAddress.getLocalHost());
    		
    	} catch (Exception e) {
    		System.err.println("Error al crear el servidor " + e.getMessage());
    	}
    }
    
    public void listen() {
        
        Socket cliente = null;
        
        do {
        
			try {
				
				// Aceptar conexion del cliente
				cliente = this.server.accept();
				
				// Crear nuevo cliente conectado
				Usuario usuarioConectado = new Usuario(cliente);
				
				// Si el usuario se conecta correctamente
				if(usuarioConectado != null) {
					System.out.println("Cliente connectado: " + usuarioConectado.getIP());
					
					// Empezar a leer datos
					usuarioConectado.start();
					
					usuarios.add(usuarioConectado);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
        } while(true);
    }
    
    
    // GETTERS & SETTERS
    
    public InetAddress getSocketAddress() {
        return this.server.getInetAddress();
    }
    
    public int getPort() {
        return this.server.getLocalPort();
    }
    
    
}