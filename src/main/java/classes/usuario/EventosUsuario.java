package classes.usuario;

public interface EventosUsuario {

	// Eventos del usuario
	void onUsuarioObjetoRecibido(Usuario usuario, Object objRecibido);
	void onUsuarioDesconectado(Usuario usuario);
	
}
