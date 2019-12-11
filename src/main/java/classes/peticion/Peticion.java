package classes.peticion;

import java.io.Serializable;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import classes.peticion.bodies.Body;
import classes.peticion.headers.Header;

public class Peticion implements Serializable {

	/*
	 * Modelo de peticion: Contiene cabezera (metadatos, informacion) i cuerpo (datos)
	 */

	private static final long serialVersionUID = 1L;
	protected Header header;
	protected Body body;
	
	// CONSTRUCTOR
	public Peticion() {
		header = new Header();
		body = new Body();		
	}
	
	// METODOS
	
	
	// GETTERS & SETTERS
	
	public Header getHeader() {
		return this.header;
	}
	
	public Body getBody() {
		return this.body;
	}
	
}
