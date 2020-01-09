package cliente.vista.chats;

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
import cliente.vista.EnumEscenas;
import cliente.vista.Launcher;
import general.EventosAplicacion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class EstiloNotepadController implements Initializable, EventosAplicacion {
	
	@FXML
	private TextArea textEditor;

	Cliente cliente;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		return;
//		cliente = Launcher.getAplicacion().getCliente();
//		
//		if(cliente == null) {
//			if(!Launcher.getAplicacion().setEscena(EnumEscenas.CONEXION))
//				System.err.println("Error al abrir escena de conexion");
//			return;
//		}
//		
//		cliente.setEventos(this);
//		cliente.start();
//		
//		cliente.enviarDatosUsuario();
	}
	
	

	@FXML
	public void textEditorOnKeyPressed(KeyEvent event) {
	
		// https://stackoverflow.com/a/19066976
		
		// Ctrl+Enter => Enviar mensaje
		if (event.getCode() == KeyCode.ENTER && event.isControlDown()) {
			
			// Ver si hay texto seleccionado o es la linea escrita
			String msg;
			if(textEditor.getSelectedText().trim().length() > 0) {

				msg = textEditor.getSelectedText().trim();
				
				// Borrar mensaje del texto
				textEditor.replaceSelection("");
			
			} else {

				// Buscar linea donde se ha presionado Ctrl+Enter
				
				if(textEditor.getCaretPosition() == 0)
					return;
				
				// Cojer linea de la posicion del cursor
				int lineaStart = textEditor.getText(0, textEditor.getCaretPosition()).lastIndexOf("\n") + 1;
				int lineaEnd = textEditor.getCaretPosition();
				
				msg = textEditor.getText(lineaStart, lineaEnd);
				
				textEditor.replaceText(lineaStart, lineaEnd, "");
			}
			
			// Enviar mensaje
			cliente.getUsuario().getConexion().sendPeticion(new PeticionMensaje(msg));
			
	    }
		
	}

	
	//
	// EVENTOS CLIENTE
	//
	
	
	@Override
	public void onMensaje(Usuario usuario, PeticionMensaje peticion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMensajeConAdjuntos(Usuario usuario, PeticionMensajeConAdjuntos peticion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDatosUsuario(Usuario usuario, PeticionDatosUsuario peticion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNotificacionConexion(Usuario usuario, NotificacionConexion notificacion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNotificacionDesconexion(Usuario usuario, NotificacionDesconexion notificacion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNotificacionPerfilActualizado(Usuario usuario, NotificacionPerfilActualizado notificacion) {
		// TODO Auto-generated method stub
		
	}
	
}
