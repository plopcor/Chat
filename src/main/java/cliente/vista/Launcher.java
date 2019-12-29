package cliente.vista;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import cliente.Cliente;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

	private static Launcher aplicacion;
	
	HashMap<EnumEscenas, URL> escenasURL = new HashMap<EnumEscenas, URL>();
	HashMap<EnumEscenas, Scene> escenas = new HashMap<EnumEscenas, Scene>();
	Stage stage;
	Cliente cliente;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		// Guardar referencia al launcher/aplicacion
		aplicacion = this;
		this.stage = stage;

		// Cargar rutas de las escenas
		escenasURL.put(EnumEscenas.CONEXION, getClass().getResource("/cliente/vista/ConexionView.fxml"));
		escenasURL.put(EnumEscenas.CHAT, getClass().getResource("/cliente/vista/ChatView.fxml"));
		escenasURL.put(EnumEscenas.CHAT_Notepad, getClass().getResource("/cliente/vista/chats/EstiloNotepad.fxml"));
		// Cerrar aplicacion al cerrar GUI
		stage.setOnCloseRequest(e -> System.exit(0));
		
		// Cargar primera escena
		
		setEscena(EnumEscenas.CHAT_Notepad);
		//setEscena(EnumEscenas.CONEXION);
		stage.setTitle("Chat - Conexion");
		
		stage.show();
	}


	//
	// GETTERS & SETTERS
	//
	
	public static Launcher getAplicacion() {
		if(aplicacion == null)
			aplicacion = new Launcher();
		return aplicacion;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Stage getStage() {
		return stage;
	}

	public HashMap<EnumEscenas, Scene> getEscenas() {
		return escenas;
	}
	
	public boolean setEscena(EnumEscenas escena) {
		
		// Si ya esta creada, muestrala
		if(escenas.containsKey(escena)) {
			stage.setScene(escenas.get(escena));
			return true;
		}
		
		// Si no esta creada, cargala i guardala
		if(escenasURL.containsKey(escena)) {
			
			// Cargar escena
			Scene s;
			try {
				s = new Scene(FXMLLoader.load(escenasURL.get(escena)));
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			
			// Guardar escena en lista de creadas
			escenas.put(escena, s);
			
			// Poner escena
			stage.setScene(s);
			
			return true;
		}
		
		return false;
	}
}
