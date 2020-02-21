package general;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class Utils {

	public static boolean checkFileExists(String ruta) {
		return new File(ruta).exists();
	}
	
	// Comprovar que el recurso existe
	public static Boolean checkResource(String resourcePath) {
		return Utils.class.getResource(resourcePath) != null;
	}

	// Devolver la ruta absoluta de un recurso
	public static String getResourceAbsolutePath(String resourcePath) {
		try {
			
			System.out.println("@URI: " + Utils.class.getResource(resourcePath).toURI());
			
			return Paths.get(Utils.class.getResource(resourcePath).toURI()).toString();
		} catch (URISyntaxException e) {
			return null;
		}
	}
	

}
