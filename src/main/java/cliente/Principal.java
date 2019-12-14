package cliente;

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
			
			client = new Cliente(ip, port);
			System.out.println("[Conectado] Servidor: " + client.getUsuario().getConexion().getSocket().getInetAddress());
			client.start();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error al conectarse al servidor");
		}

	}

}
