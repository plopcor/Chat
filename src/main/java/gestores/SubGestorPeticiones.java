package gestores;

import classes.Perfil;
import classes.peticion.*;
import classes.usuario.Usuario;

public class SubGestorPeticiones {

	private EventosSubGestorPeticiones eventos;

	
	//
	// CONSTRUCTOR
	//
	
	private SubGestorPeticiones() {
		super();
	}
	
	public SubGestorPeticiones(EventosSubGestorPeticiones eventos) {
		this();
		this.eventos = eventos;
	}
	
	//
	// METODOS
	//
	
	public void procesar(Usuario usuario, Peticion peticion) {
		
		if(peticion instanceof PeticionDatosUsuario)
			procesarPeticionDatos(usuario, (PeticionDatosUsuario) peticion);
			
		else if (peticion instanceof PeticionMensaje)
			if(peticion instanceof PeticionMensajeConAdjuntos)
				procesarPeticionMensajeConAdjunto(usuario, (PeticionMensajeConAdjuntos) peticion);
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
		
		// Comprovar que tiene datos
		Perfil perf = peticion.getPerfil();
		
		// Sanitize input
		perf.setNombre(perf.getNombre().trim());
		
		// Validate input values
		if(perf.getNombre().isEmpty())
			return;

		else if (perf.getNombre().length() > 30)
			perf.setNombre(perf.getNombre().substring(0, 30));
		
		if(eventos != null)
			eventos.onProcesadoDatosUsuario(usuario, peticion);
	}
	
	private void procesarPeticionMensaje(Usuario usuario, PeticionMensaje peticion) {

		peticion.setMensaje(sanitizeMensaje(peticion.getMensaje()));
		
		if(peticion.getMensaje().length() == 0)
			return;
		
		if(eventos != null)
			eventos.onProcesadoMensaje(usuario, peticion);
	}
	
	private void procesarPeticionMensajeConAdjunto(Usuario usuario, PeticionMensajeConAdjuntos peticion) {
		
		// Si no tiene adjuntos, 
		if(peticion.getFicheros() == null || peticion.getFicheros().size() == 0) {
			if(eventos != null)
				procesarPeticionMensaje(usuario, (PeticionMensaje) peticion);
			return;
		}

		// Validar mensaje
		peticion.setMensaje(sanitizeMensaje(peticion.getMensaje()));
		
		if(peticion.getMensaje().length() == 0)
			return;

		if(eventos != null)
			eventos.onProcesadoMensajeConAdjuntos(usuario, peticion);		
	}

	
	//
	// SETTERS & GETTERS
	//
	
	public void setEventos(EventosSubGestorPeticiones eventos) {
		this.eventos = eventos;
	}

	//
	// METODOS
	//
	private String sanitizeMensaje(String msg) {

		msg = msg.trim();
		
		if(msg.length() > 250)
			msg = msg.substring(0, 250);
		
		return msg;
	}
}
