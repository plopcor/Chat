package classes.peticiones;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Peticion {

	/*
	 * Modelo de peticion: Contiene cabezera (metadatos, informacion) i cuerpo (datos)
	 */
	
	protected HashMap<String, String> headers;
	protected HashMap<String, String> body;
	
	// CONSTRUCTOR
	public Peticion() {
		
		headers = new HashMap<String, String>();
		body = new HashMap<String, String>();
		
	}
	
	// METODOS
	
	public void addHeader(String k, String v) {
		headers.put(k, v);
	}
	
	public void addData(String k, String v) {
		body.put(k, v);
	}
	
	public Peticion fromJSONString(String jsonString) {
		
		Peticion p = new Peticion();
		JSONParser jsonParser = new JSONParser();
		
		try {
			
			JSONObject jsonObj = (JSONObject) jsonParser.parse(jsonString);
			
			if(!jsonObj.containsKey("header") || !jsonObj.containsKey("body"))
				throw new ParseException(0, "Peticion no tiene header ni body");

			this.headers = (JSONObject) jsonObj.get("header");
			this.body = (JSONObject) jsonObj.get("body");	
			
		} catch (ParseException e) {
			System.err.println("Error: Peticion mal formada");
			e.printStackTrace();
			return null;
		}
		
		return p;
	}
	
	@Override
	public String toString() {
		
		String str = "";
		str += "Headers\n" + this.headers + "\n";
		str += "Body\n" + this.body + "\n";
		
		return str;
	}
	
	public JSONObject toJSON() {
		
		JSONObject jsonObj = new JSONObject();
		
		// Add headers map
		jsonObj.put("header", new JSONObject(this.headers));
		
		// Add body map
		jsonObj.put("body", new JSONObject(this.body));
		
		return jsonObj;
	}
	
	public String toJSONString() {
		return toJSON().toJSONString();
	}
	
	
	// GETTERS & SETTERS
	
	public HashMap<String, String> getHeaders() {
		return this.headers;
	}
	
	public HashMap<String, String> getBody() {
		return this.body;
	}
	
}
