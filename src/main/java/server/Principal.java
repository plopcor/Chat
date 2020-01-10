package server;

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
				System.err.println("Numero de puerto no valido, valido de 0 a 65535");
				port = -1;
			}

			scn.nextLine();
			
		} while (port < 0);
		
		// Iniciar servidor
		Servidor srv = new Servidor(null, port);
        System.out.println("Servidor iniciado");
        try {
            System.out.println("- Host: " + srv.getSocketAddress().getHostAddress());        	
            System.out.println("- Port: " + srv.getPort());
             
        } catch (Exception e) {
        	System.out.println("Error: " + e.getMessage());
        }
        
        // Recojer eventos del backend
        //srv.getBackend().setEventos(this);
    
        srv.listen();
		
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
