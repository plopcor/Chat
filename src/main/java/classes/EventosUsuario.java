package classes;

public interface EventosUsuario {

	void onMensajeRecibido(Usuario usuario, String mensaje);
	void onDesconectado(Usuario usuario);
	
}
