package Metamapa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.awt.Desktop;
import java.net.URI;

/**
 * Punto de entrada principal de MetaMapa.
 * - Levanta el API REST (/api/...)
 * - Sirve la interfaz web estática (src/main/resources/static/)
 * - Expone /config.js para que el front lea la URL base del agregador
 */
@SpringBootApplication
public class MetamapaApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(MetamapaApplication.class, args);
		try {
			String port = context.getEnvironment().getProperty("server.port", "9000");
			String url = "http://localhost:" + port + "/";
			System.out.println("🌐 MetaMapa iniciado en: " + url);
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().browse(new URI(url));
			}
		} catch (Exception e) {
			System.err.println("No se pudo abrir el navegador: " + e.getMessage());
		}
	}
}