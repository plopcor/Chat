package cliente.vista;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.net.InetAddress;
import java.net.UnknownHostException;

import cliente.Cliente;
import javafx.event.ActionEvent;

public class ConexionController {
	
	@FXML
	private TextField inputIP;
	@FXML
	private TextField inputPort;
	@FXML
	private Button btnConectar;

	InetAddress ip;
	int port;
	
	@FXML
	public void inputIPEnter(ActionEvent event) {
		if(!inputIP.getText().trim().isEmpty())
			inputPort.requestFocus();
	}
	@FXML
	public void inputPortEnter(ActionEvent event) {
		if(!inputPort.getText().trim().isEmpty())
			btnConectar.fire();
	}
	@FXML
	public void btnConectarClick(ActionEvent event) {
		
		// Validar IP
		try {
			ip = InetAddress.getByName(inputIP.getText().trim());
			
		} catch (UnknownHostException e) {
			System.err.println("[Error] IP no valida");
			return;
		}
		
		
		// Validate Port
		try {
			port = Integer.parseInt(inputPort.getText().trim());
			
		} catch (NumberFormatException e) {
			
			System.err.println("[Error] Numero de puerto no valido");
		}
		
		if(port < 0 || port > 65535) {
			System.err.println("[Error] El puerto ha de estar entre 0 y 65535");
			return;
		}
		
		String[] certificado = new String[2];
		
		//System.out.println("[Certificado] Ruta del certificado:");
		certificado[0] = "./certificados/ssl_rsa_cert.p12";
		
		//System.out.println("[Certificado] Contraseña:");
		certificado[1] = "12345";
		
		// Crear conexion
		Launcher.getAplicacion().setCliente(new Cliente(ip, port, certificado));
		
		// Mostrar chat
		Launcher.getAplicacion().setEscena(EnumEscenas.CHAT);
		
	}
	
}
