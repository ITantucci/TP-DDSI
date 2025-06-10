package domain.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"domain.business",
		"infrastructure.controller",
		"infrastructure.dto",
		"infrastructure.mapper",
		"infrastructure.config" // <-- agregá este si no lo tenías
})
public class Metamapa {
	public static void main(String[] args) {
		SpringApplication.run(Metamapa.class, args);
	}
}
