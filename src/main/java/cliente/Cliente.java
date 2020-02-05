package cliente;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;


import classes.Perfil;
import classes.notificacion.*;
import classes.peticion.*;
import classes.usuario.Usuario;
import general.EventosAplicacion;
import gestores.GestorGeneral;

public class Cliente implements EventosAplicacion {

	private Usuario usuario;
    private Scanner scn;
    private static boolean debugMode = false;
    private GestorGeneral gestor;
    private EventosAplicacion eventos;
     
 // SSL Key
    private String SSL_KEY_PATH = "/cliente/ssl_rsa_cert.p12";
    private String SSL_KEY_PASSWORD = "123456";
    
    public Cliente (InetAddress serverAddress, int serverPort) {
        
    	// Comprovar que el certificado existe
    	if(getClass().getResource(SSL_KEY_PATH) == null) {
    		System.err.println("No existe el certificado en la ruta => " + SSL_KEY_PATH);
    		System.exit(0);
    	} else
    		SSL_KEY_PATH = getClass().getResource(SSL_KEY_PATH).getPath();
    	
    	// Set SSL key
    	System.setProperty("javax.net.ssl.trustStore", SSL_KEY_PATH);
		System.setProperty("javax.net.ssl.trustStorePassword", SSL_KEY_PASSWORD);
		System.setProperty("javax.net.ssl.keyStore", SSL_KEY_PATH);
		System.setProperty("javax.net.ssl.keyStorePassword", SSL_KEY_PASSWORD);
    	
    	try {

    		// Crear SSL Socket y habilitar protocolos y cipher suites
        	SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(serverAddress, serverPort);
        	socket.setEnabledProtocols(new String[] {"TLSv1.2"});
        	socket.setEnabledCipherSuites(new String[] {"TLS_RSA_WITH_AES_256_GCM_SHA384"});
        	
        	// Crear usuario, conexion y gestor
        	this.usuario = new Usuario(socket);  //new Usuario(new Socket(serverAddress, serverPort));
        	this.gestor = new GestorGeneral(this);
        	this.scn = new Scanner(System.in);
        	
        	// Enviar eventos del usuario al gestor
        	this.usuario.setEventos(gestor);
        	
		} catch (IOException e) {
			e.printStackTrace();
		}       
    }
    
    public void start() {
    	
    	// Empezar lectura para datos recibidos
    	this.usuario.start();
    	
    	// Si se controla desde fuera, se enviaran los datos desde fuera (no leera datos de la consola)
    	if(eventos != null)
    		return;
    	
    	enviarDatosUsuario();
    	
    	String input;

		// Empezar lectura para enviar datos
        while (true) {

        	input = scn.nextLine().trim();
        	
        	// Crear PeticioMensaje con el mensaje
        	if(!input.isEmpty())
        		this.getUsuario().getConexion().sendMensaje(input);
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
    
    public static void log(String texto) {
		if(debugMode)
			System.out.println(texto);
	}
    
    
    // GETTERS & SETTERS
    public Usuario getUsuario() {
    	return this.usuario;
    }

	public GestorGeneral getGestor() {
		return gestor;
	}
	
	public void setEventos(EventosAplicacion eventos) {
		this.eventos = eventos;
	}

	//
	// EVENTOS
	//
	
	@Override
	public void onMensaje(Usuario usuario, PeticionMensaje peticion) {
		log("@ [MENSAJE] " + peticion.getHeader().getPerfilEmisor().getNombre() + " => " + peticion.getMensaje());

		// Si tiene handler, re-enviar al handler. Si no, mostrar aqui
    	if(eventos != null)
    		eventos.onMensaje(usuario, peticion);
    	else
    		System.out.println(peticion.getHeader().getPerfilEmisor().getNombre() + ": " + peticion.getMensaje());
		
	}

	@Override
	public void onMensajeConAdjuntos(Usuario usuario, PeticionMensajeConAdjuntos peticion) {
		log("@ [MENSAJE][ADJUNTOS] " + peticion.getHeader().getPerfilEmisor().getNombre() + " => " + peticion.getMensaje() + " /// *ADJUNTOS*");
		
		// Si tiene handler, re-enviar al handler. Si no, mostrar aqui
    	if(eventos != null)
    		eventos.onMensajeConAdjuntos(usuario, peticion);
    	else
    		System.out.println(peticion.getHeader().getPerfilEmisor().getNombre() + ": " + peticion.getMensaje() + " /// [Adjuntos] ");
		
	}

	@Override
	public void onDatosUsuario(Usuario usuario, PeticionDatosUsuario peticion) {
		log("@ [DATOS USUARIO] " + peticion.getHeader().getPerfilEmisor().getNombre() + "=> " + peticion.getPerfil().getNombre());
	}

	@Override
	public void onNotificacionConexion(Usuario usuario, NotificacionConexion notificacion) {
		log("@ [CONECTADO] => " + notificacion.getPerfilUsuario().getNombre());

		if(eventos != null)
			eventos.onNotificacionConexion(usuario, notificacion);
		else
			System.out.println("[Conectado] " + notificacion.getPerfilUsuario().getNombre());
	}

	@Override
	public void onNotificacionDesconexion(Usuario usuario, NotificacionDesconexion notificacion) {
		log("@ [DESCONECTADO] => " + notificacion.getPerfilUsuario().getNombre());
		
		if(eventos != null)
			eventos.onNotificacionDesconexion(usuario, notificacion);
		else
			System.out.println("[Desconectado] " + notificacion.getPerfilUsuario().getNombre());
	}

	@Override
	public void onNotificacionPerfilActualizado(Usuario usuario, NotificacionPerfilActualizado notificacion) {
		log("@ [PERFIL ACTUALIZADO] => " + notificacion.toString());
		
		if(eventos != null)
			eventos.onNotificacionPerfilActualizado(usuario, notificacion);
		else
			System.out.println("[Perfil] " + notificacion.getPerfilAntiguo().getNombre() + " ha actualizado su perfil a " + notificacion.getPerfilNuevo().getNombre());		
	}
	
}
