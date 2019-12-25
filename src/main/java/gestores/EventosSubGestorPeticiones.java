package gestores;

import classes.peticion.*;
import classes.usuario.Usuario;

public interface EventosSubGestorPeticiones {

	// Peticiones
	void onProcesadoMensaje(Usuario usuario, PeticionMensaje peticion);
	void onProcesadoMensajeConAdjuntos(Usuario usuario, PeticionMensajeConAdjuntos peticion);
	void onProcesadoDatosUsuario(Usuario usuario, PeticionDatosUsuario peticion);
	
}
