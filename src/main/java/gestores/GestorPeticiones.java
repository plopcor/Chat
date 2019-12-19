package gestores;

import classes.Perfil;
import classes.peticion.*;
import classes.usuario.Usuario;
import cliente.Cliente;

public class GestorPeticiones {

	private EventosGestor eventos;

	
	//
	// CONSTRUCTOR
	//
	
	public GestorPeticiones() {
	}
	
	public GestorPeticiones(EventosGestor eventos) {
		this.eventos = eventos;
	}
	
	//
	// METODOS
	//
	
	public void procesar(Usuario usuario, Peticion peticion) {

		Cliente.log("@ Recibido peticion de \"" + usuario.getPerfil().getNombre() + "\"");
		
		if(peticion instanceof PeticionDatosUsuario)
			procesarPeticionDatos(usuario, (PeticionDatosUsuario) peticion);
			
		else if (peticion instanceof PeticionMensaje)
			if(peticion instanceof PeticionMensajeConAdjuntos)
				procesarPeticionMensajeConAdjunto(usuario, (PeticionMensaje) peticion);
			else
				procesarPeticionMensaje(usuario, (PeticionMensaje) peticion);
		
		else {
			System.err.println("[INFO] No se ha podido procesar la peticion");
			System.err.println("[INFO] Tipo de peticion desconocido => \""+ peticion.getClass().getSimpleName() + "\"");
		}
	}
	
	
	
	
	//
	// PROCESADORES
	//

	private void procesarPeticionDatos(Usuario usuario, PeticionDatosUsuario peticion) {
		// DEBUG
		Cliente.log("# RECIBIDO => Datos de usuario");
	}
	
	private void procesarPeticionMensaje(Usuario usuario, PeticionMensaje peticion) {
		
		// DEBUG
		Cliente.log("# RECIBIDO => Mensaje");
		
		if(peticion.getMensaje().length() == 0)
			return;
		
		if(eventos != null)
			eventos.onProcesadoMensaje(usuario, peticion);
	}
	
	private void procesarPeticionMensajeConAdjunto(Usuario usuario, PeticionMensaje peticion) {
		if(eventos != null)
			eventos.onProcesadoMensaje(usuario, peticion);		
	}

	
	//
	// SETTERS & GETTERS
	//
	
	public void setEventos(EventosGestor eventos) {
		this.eventos = eventos;
	}

	
}
