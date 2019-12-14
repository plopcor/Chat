package classes.usuario;

public interface EventosUsuario {

	// Usuario X recibe objeto Y
	void onUsuarioObjetoRecibido(Usuario usuario, Object objRecibido);
	
//	void onMensajeRecibido(Usuario usuario, Object objRecibido);
//	void onDesconectado(Usuario usuario);
	
}
