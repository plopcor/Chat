package server;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {

	public static Scanner scn = new Scanner(System.in);

	public static void main(String[] args) {
		int port;

		do {

			System.out.println("Puerto para el servidor:");

			try {
				port = scn.nextInt();
			} catch (InputMismatchException e) {
				System.err.println("Numero de puerto no valido, valido: 0 a 65535");
				port = -1;
			}

		} while (port < 0);
		
		// Iniciar servidor
		Servidor srv = new Servidor(null, port);
        System.out.println("Servidor iniciado");
        System.out.println("- Host: " + srv.getSocketAddress().getHostAddress());
        System.out.println("- Port: " + srv.getPort());
        
        srv.listen();
		
	}
}
