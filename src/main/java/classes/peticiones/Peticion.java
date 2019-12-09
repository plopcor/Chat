package classes.peticiones;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Peticion {

	/*
	 * Modelo de peticion: Contiene cabezera (metadatos, informacion) i cuerpo (datos)
	 */
	
	protected JSONObject json;
	protected JSONObject jsonHeader;
	protected JSONObject jsonBody;
	
	// CONSTRUCTOR
	public Peticion() {
		json = new JSONObject();
		jsonHeader = new JSONObject();
		jsonBody = new JSONObject();
		
		json.put("header", jsonHeader);
		json.put("body", jsonBody);
		
	}
	
	// METODOS
	
	public void addData(Object k, Object v) {
		jsonBody.put(k, v);
	}
	
	public Peticion fromJSONString(String jsonString) {
		
		Peticion p = new Peticion();
		JSONParser jsonParser = new JSONParser();
		
		try {
			
			JSONObject jsonObj = (JSONObject) jsonParser.parse(jsonString);
			
			if(!jsonObj.containsKey("header") || !jsonObj.containsKey("body"))
				throw new ParseException(0, "Peticion no tiene header ni body");
			
			p.json = jsonObj;
			
			
			
		} catch (ParseException e) {
			System.err.println("Error: Peticio mal formada");
			e.printStackTrace();
			return null;
		}
		
		return p;
	}
	
	@Override
	public String toString() {
		return json.toString();
	}
	
	public String toJSONString() {
		return json.toJSONString();
	}
	
}
