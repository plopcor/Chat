package classes.usuarios;

public interface EventosUsuario {

	void onMensajeRecibido(Usuario usuario, String mensaje);
	void onDesconectado(Usuario usuario);
	
}
