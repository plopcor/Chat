package servidor;

import java.io.IOException;
import java.net.InetAddress;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import classes.usuario.Usuario;
import general.Utils;

public class Servidor {
	
    private SSLServerSocket server;
    private Backend backend;
    
    // SSL Key
    private String SSL_KEY_PATH = "/servidor/ssl_rsa_cert.p12";
    private String SSL_KEY_PASSWORD = "123456";
    
    public Servidor(String ipAddress, int port) {
    	
    	// Comprovar que el certificado existe i cojer la ruta absoluta
    	SSL_KEY_PATH = Utils.getResourceAbsolutePath(SSL_KEY_PATH);
    	if(SSL_KEY_PATH.isEmpty()) {
    		System.err.println("No existe el certificado en la ruta => " + SSL_KEY_PATH);
    		System.exit(0);
    	}
    	
    	// Certificado, SSL Key
    	System.setProperty("javax.net.ssl.trustStore", SSL_KEY_PATH);
		System.setProperty("javax.net.ssl.trustStorePassword", SSL_KEY_PASSWORD);
		System.setProperty("javax.net.ssl.keyStore", SSL_KEY_PATH);
		System.setProperty("javax.net.ssl.keyStorePassword", SSL_KEY_PASSWORD);
		
    	
    	try {
    		
    		// Creat SSLServerSocket
            SSLServerSocket socket = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(port); //, 0, InetAddress.getLocalHost());
            socket.setEnabledProtocols(new String[] {"TLSv1.2"});
            socket.setEnabledCipherSuites(new String[] {"TLS_RSA_WITH_AES_256_GCM_SHA384", "TLS_RSA_WITH_AES_256_CBC_SHA256"});  //TLS_RSA_WITH_AES_256_CBC_SHA //, "TLS_AES_128_GCM_SHA256"});
            
            this.server = socket;
			
    	} catch (IllegalArgumentException e) {
    		System.err.println("Error, el puerto ha de estar entre 0 y 65535");
    		e.printStackTrace();
    		
    	} catch (Exception e) {
    		System.err.println("Error al crear el servidor: " + e.getMessage());
    		System.exit(0);
    	}
    	
    	backend = new Backend();
    }
    
    
    public void listen() {
    	
        SSLSocket cliente = null;
        
        do {
        
			try {
				
				// Aceptar conexion del cliente
				cliente = (SSLSocket) this.server.accept();
				
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
    
    public Backend getBackend() {
    	return this.backend;
    }
    
    
}