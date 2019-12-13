package classes.usuarios;

public interface EventosUsuario {

	void onMensajeRecibido(Usuario usuario, Object objRecibido);
	void onDesconectado(Usuario usuario);
	
}
