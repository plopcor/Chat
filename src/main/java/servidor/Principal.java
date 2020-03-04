package servidor;

import java.util.InputMismatchException;
import java.util.Scanner;

import classes.notificacion.NotificacionConexion;
import classes.notificacion.NotificacionDesconexion;
import classes.notificacion.NotificacionPerfilActualizado;
import classes.peticion.PeticionDatosUsuario;
import classes.peticion.PeticionMensaje;
import classes.peticion.PeticionMensajeConAdjuntos;
import classes.usuario.Usuario;
import general.EventosAplicacion;

public class Principal implements EventosAplicacion {

	public static Scanner scn = new Scanner(System.in);


	public static void main(String[] args) {
		new Principal().iniciar();
	}
	
	public void iniciar() {
		int port;

		do {

			System.out.println("Puerto para el servidor:");

			try {
				port = scn.nextInt();

			} catch (InputMismatchException e) {
				port = -1;
			}

			if(port < 0 || port > 65535) {
				System.err.println("Numero de puerto no valido, valido de 0 a 65535");
			}
			
			scn.nextLine();
			System.out.println();
			
		} while (port < 0 || port > 65535);
		
		
		String[] certificado = new String[2];
		
		//System.out.println("[Certificado] Ruta del certificado:");
		certificado[0] = "./certificados/ssl_rsa_cert.p12";
		
		//System.out.println("[Certificado] Contraseña:");
		certificado[1] = "123456";
		
		
		
		// Iniciar servidor
		Servidor srv;
        try {
        	
        	srv = new Servidor(null, port, certificado);

        	System.out.println("Servidor iniciado");
            System.out.println("- Host: " + srv.getSocketAddress().getHostAddress() + " => " + srv.getSocketAddress().getCanonicalHostName() + " => " + srv.getSocketAddress().getHostName());        	
            System.out.println("- Port: " + srv.getPort());
             
            // Recojer eventos del backend
            //srv.getBackend().setEventos(this);
        
            srv.listen();
            
        } catch (Exception e) {
        	System.err.println("Error: " + e.getMessage());
        }
		
	}

	@Override
	public void onMensaje(Usuario usuario, PeticionMensaje peticion) {
		System.out.println("[" + usuario.getPerfil().getNombre() + "]: " + peticion.getMensaje());
	}

	@Override
	public void onMensajeConAdjuntos(Usuario usuario, PeticionMensajeConAdjuntos peticion) {
		System.out.println("[" + usuario.getPerfil().getNombre() + "]: " + peticion.getMensaje());
		System.out.println("*Adjuntos*");
	}

	@Override
	public void onDatosUsuario(Usuario usuario, PeticionDatosUsuario peticion) {
		System.out.println("Informacion de perfil cambiada => " + peticion.getPerfil().getNombre());
	}

	@Override
	public void onNotificacionConexion(Usuario usuario, NotificacionConexion notificacion) {
		System.out.println("Usuario " + notificacion.getPerfilUsuario().getNombre() + " conectado.");
	}

	@Override
	public void onNotificacionDesconexion(Usuario usuario, NotificacionDesconexion notificacion) {
		System.out.println("Usuario " + notificacion.getPerfilUsuario().getNombre() + " desconectado.");		
	}

	@Override
	public void onNotificacionPerfilActualizado(Usuario usuario, NotificacionPerfilActualizado notificacion) {
		System.out.println("[ERROR] EL SERVIDOR NO HA DE RECIBIR ESTA NOTIFICACION, YA LA CREA EL AL RECIBIR UNA PETICION DE DATOS");
	}
}
