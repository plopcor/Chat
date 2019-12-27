package cliente;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
		
		InetAddress ip;
		int port;
		
		// Pedir IP i puerto del servidor
		do {

			System.out.print("\nIP del servidor: ");
			
			try {
				ip = InetAddress.getByName(scn.nextLine());
				
			} catch (UnknownHostException e) {
				System.err.println("[Error] IP no valida");
				//e.printStackTrace();
				ip = null;
			}
		} while(ip == null);
		
		do {
			
			System.out.print("Port: ");
			
			try {
				port = scn.nextInt();
			} catch (InputMismatchException e) {
				System.err.println("\nNumero de puerto no valido");
				port = -1;
				scn.nextLine();
			}
			
		} while(port < 0);
		
		
		
		// Abrir conexion
		
		Cliente client = null;
		try {
			
			client = new Cliente(ip, port, null);
			System.out.println("[Conectado] Servidor: " + client.getUsuario().getConexion().getSocket().getInetAddress());
			
			// Recojer eventos del cliente
			//client.setEventos(this);			
			
			client.start();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error al conectarse al servidor");
		}

	}

	@Override
	public void onMensaje(Usuario usuario, PeticionMensaje peticion) {
		System.out.println("[" + usuario.getPerfil().getNombre() + "]: " + peticion.getMensaje());
	}

	@Override
	public void onMensajeConAdjuntos(Usuario usuario, PeticionMensajeConAdjuntos peticion) {
		System.out.println("[" + usuario.getPerfil().getNombre() + "]: " + peticion.getMensaje());
		System.out.println("*Ajuntos*");
	}

	@Override
	public void onDatosUsuario(Usuario usuario, PeticionDatosUsuario peticion) {
		System.out.println("[ERROR] EL CLIENTE NO DEBERIA RECIBIR DATOS DE USUARIO");
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
		System.out.println("Usuario " + notificacion.getPerfilNuevo().getNombre() + " ha actualizado el perfil");
		System.out.println(notificacion);
		
	}

}
