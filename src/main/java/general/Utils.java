package general;

import java.io.File;

public class Utils {

	public static boolean ficheroExiste(String ruta) {
		return new File(ruta).exists();
	}
	
}
