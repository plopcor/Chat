package general;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

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

	// Comprovar que la contraseña del keystore es correcta
	public static boolean validateKeystorePassword(String keystorePath, String password) {

		try {
			FileInputStream is = new FileInputStream(new File(keystorePath));
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			keystore.load(is, password.toCharArray());
			return true;
			
		} catch (NoSuchAlgorithmException | CertificateException | IOException | KeyStoreException e) {
			System.err.println("Error al validar KeyStore (" + e.getClass().getCanonicalName() + "): " + e.getMessage());
		}

		return false;
	}

}
