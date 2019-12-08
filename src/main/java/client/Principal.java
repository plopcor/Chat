package client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {

	public static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		InetAddress ip;
		int port;
		
		// Pedir IP i puerto del servidor
		do {

			System.out.println("IP del servidor:");
			
			try {
				ip = InetAddress.getByName(scn.nextLine());
			} catch (UnknownHostException e) {
				e.printStackTrace();
				ip = null;
			}
		} while(ip == null);
		
		do {
			
			System.out.println("Port:");
			
			try {
				port = scn.nextInt();
			} catch (InputMismatchException e) {
				System.err.println("Numero de puerto no valido");
				port = -1;
				scn.nextLine();
			}
			
		} while(port < 0);
		
		
		
		// Abrir conexion
		
		Cliente client = null;
		try {
			
			client = new Cliente(ip, port);
			System.out.println("Conectado al servidor: " + client.getUsuario().getConexion().getSocket().getInetAddress());
			client.start();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error al conectarse al servidor");
		}

	}

}
