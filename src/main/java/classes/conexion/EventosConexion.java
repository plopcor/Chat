package classes.conexion;

public interface EventosConexion {
	void onMensajeEnviado(String mensaje);
	void onDesconectado();
}
