package servidor;

import java.io.File;
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
    private String SSL_KEY_PATH;
    private String SSL_KEY_PASSWORD;
    
    public Servidor(String ipAddress, int port, String[] certificado) {
    	
    	// Comprovaciones
    	if(port < 0 || port > 65535)
    		throw new IllegalArgumentException("El puerto ha de estar entre 0 y 65535");
    	
		try {

			// Resolver ruta
			SSL_KEY_PATH = new File(certificado[0]).getCanonicalFile().getAbsolutePath();
			SSL_KEY_PASSWORD = certificado[1];

			// Comprovar que la ruta existe
			if (!Utils.checkFileExists(SSL_KEY_PATH))
				throw new IllegalArgumentException("No se ha encontrado el certificado en la ruta => " + SSL_KEY_PATH);

			// Comprovar que la contraseña es valida
			if(!Utils.validateKeystorePassword(SSL_KEY_PATH, SSL_KEY_PASSWORD)) {
				throw new IOException("Contraseña del certificado invalida");
			}
		
		} catch (IOException e1) {
			throw new IllegalArgumentException("Error al leer el certificado => " + e1.getMessage());
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
            this.backend = new Backend();

    	} catch (Exception e) {
    		throw new NullPointerException("Error al crear el servidor => " + e.getMessage());
    	}
    	
    }
    
    
    public void listen() {
    	
        SSLSocket cliente = null;
        
        do {
        
			try {
				
				// Aceptar conexion del cliente
				cliente = (SSLSocket) this.server.accept();
				
				// Crear nuevo usuario (cliente conectado)
				Usuario usuarioConectado = new Usuario(cliente);
				
				// Añadir a la lista i empezar a leer datos
				backend.conectarUsuario(usuarioConectado);
				
			} catch (IOException e) {
				System.err.println("Error al aceptar conexion de un cliente");
				System.err.println(e.getMessage());
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