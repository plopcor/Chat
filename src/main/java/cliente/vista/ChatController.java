package cliente.vista;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import classes.notificacion.NotificacionConexion;
import classes.notificacion.NotificacionDesconexion;
import classes.notificacion.NotificacionPerfilActualizado;
import classes.peticion.PeticionDatosUsuario;
import classes.peticion.PeticionMensaje;
import classes.peticion.PeticionMensajeConAdjuntos;
import classes.usuario.Usuario;
import cliente.Cliente;
import general.EventosAplicacion;
import javafx.event.ActionEvent;

import javafx.scene.control.TextArea;

public class ChatController implements Initializable, EventosAplicacion {
	@FXML
	private TextArea txtChat;
	@FXML
	private TextField inputMensaje;
	@FXML
	private Button btnEnviar;
	
	Cliente cliente;
	
	@FXML
	public void inputMensajeEnter(ActionEvent event) {
		String msg = inputMensaje.getText().trim();
		inputMensaje.setText("");
		
		if(msg.isEmpty())
			return;
		
		txtChat.appendText("[" + cliente.getUsuario().getPerfil().getNombre() + "]: " + msg + "\n");
		
		cliente.getUsuario().getConexion().sendPeticion(new PeticionMensaje(msg));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		cliente = Launcher.getAplicacion().getCliente();
		
		if(cliente == null) {
			if(!Launcher.getAplicacion().setEscena(EnumEscenas.CONEXION))
				System.err.println("Error al abrir escena de conexion");
			return;
		}
		
		cliente.setEventos(this);
		cliente.start();
		
		cliente.enviarDatosUsuario();
		
		inputMensaje.requestFocus();
	}

	
	//
	// EVENTOS CLIENTE
	//
	
	@Override
	public void onMensaje(Usuario usuario, PeticionMensaje peticion) {
		this.txtChat.appendText("[" + usuario.getPerfil().getNombre() + "]: " + peticion.getMensaje() + "\n");
	}

	@Override
	public void onMensajeConAdjuntos(Usuario usuario, PeticionMensajeConAdjuntos peticion) {
		this.txtChat.appendText("[" + usuario.getPerfil().getNombre() + "]: " + peticion.getMensaje() + "\n");

		// Mostrar informacion de los adjuntos
		int max = 5, i = 0;
		for(classes.Fichero f : peticion.getFicheros()) {
			if(i == max)
				return;
			System.out.println("- " + f.getFichero().getName() + " (Size: " + (f.getBytesFichero().length / 1024) + " kb)");
			i++;
		}
	}

	@Override
	public void onDatosUsuario(Usuario usuario, PeticionDatosUsuario peticion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNotificacionConexion(Usuario usuario, NotificacionConexion notificacion) {
		txtChat.appendText(notificacion.getPerfilUsuario().getNombre() + " se ha conectado.\n");
	}

	@Override
	public void onNotificacionDesconexion(Usuario usuario, NotificacionDesconexion notificacion) {
		txtChat.appendText(notificacion.getPerfilUsuario().getNombre() + " se ha desconectado.\n");
	}

	@Override
	public void onNotificacionPerfilActualizado(Usuario usuario, NotificacionPerfilActualizado notificacion) {
		txtChat.appendText(notificacion.getPerfilNuevo().getNombre() + " ha actualizado su perfil\n");
		txtChat.appendText(notificacion.toString());
	}

	
}
