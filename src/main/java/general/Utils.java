package general;

import java.net.URISyntaxException;
import java.nio.file.Paths;

public class Utils {
	
	// Retornar la ruta de un resource (tambien comprueba que existe)
	public static String getResourceAbsolutePath(String resourcePath) {
		
		String path = "";
		
		if(Utils.class.getResource(resourcePath) != null) {
			try {
				path = Paths.get(Utils.class.getResource(resourcePath).toURI()).toString();
			} catch (URISyntaxException e) {
			}
		}
		return path;
	}
	
	
	
}
