package domain;

import ch.qos.logback.core.joran.sanity.Pair;
import domain.business.FuentesDeDatos.FuenteDeDatos;
import domain.business.FuentesDeDatos.FuenteEstatica;
import domain.business.Parsers.CSVHechoParser;
import domain.business.Parsers.HechoParser;
import domain.business.Usuarios.Perfil;
import domain.business.Usuarios.Usuario;
import domain.business.criterio.Coleccion;
import domain.business.criterio.CriterioCategoria;
import domain.business.criterio.CriterioFecha;
import domain.business.tiposSolicitudes.SolicitudEliminacion;
import java.nio.channels.MulticastChannel;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import domain.business.FuentesDeDatos.FuenteDinamica;
import domain.business.FuentesDeDatos.FuenteProxy;
import domain.business.Usuarios.Rol;
import domain.business.incidencias.Hecho;
import domain.business.incidencias.Multimedia;
import domain.business.Agregador.Agregador;
import java.util.ArrayList;
import domain.business.incidencias.Ubicacion;
import java.time.LocalDate;
import domain.business.criterio.Criterio;

import domain.business.incidencias.TipoMultimedia;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class MetamapaTests {

	@Test
	//1- Como persona administradora, deseo crear una colección.
	public void crearColeccion(){
		Perfil admin01 = new Perfil("Juan", "Perez", 30);
		Usuario admin = new Usuario("admin1@frba.utn.edu.ar", "algo", admin01, List.of(Rol.ADMINISTRADOR,Rol.CONTRIBUYENTE));

		if (!admin.tieneRol(Rol.ADMINISTRADOR)) {
			throw new IllegalStateException("El usuario no tiene rol de ADMINISTRADOR.");
		}
		FuenteDinamica fuente = new FuenteDinamica();
		fuente.agregarHecho("Incendio en el monumental",
				"El club atletico River Plate descendio a la primera nacional y sus hinchas quemaron la cancha",
			"futbol",0,0,);




		Agregador agregador = new Agregador(new ArrayList<>());
		Coleccion coleccion = new Coleccion("Incendios 2025", "incendios",null,null, null);
	}


	// 2- Como persona administradora, deseo poder importar hechos desde un archivo CSV
	@Test
	public void importarHechos() {
		Perfil admin01 = new Perfil("Juan", "Perez", 30);
		Usuario admin = new Usuario("admin1@frba.utn.edu.ar", "algo", admin01, List.of(Rol.ADMINISTRADOR, Rol.CONTRIBUYENTE));

		if (!admin.tieneRol(Rol.ADMINISTRADOR)) {
			throw new IllegalStateException("El usuario no tiene rol de ADMINISTRADOR.");
		}
		
		String path = "ruta";
		CSVHechoParser parser = new CSVHechoParser();
		FuenteEstatica fuente = new FuenteEstatica(path, parser);

		fuente.cargarCSV();

		assertThat(fuente.getListaHecho()).isNotEmpty();

	}

//TODO: 3- Como persona visualizadora, deseo navegar todos los hechos disponibles de una colección.
	@Test
	public void navegarHechosDeColeccion(){
		Perfil admin01 = new Perfil("Juan", "Perez", 30);
		Usuario admin = new Usuario("admin1@frba.utn.edu.ar", "algo", admin01, List.of(Rol.VISUALIZADOR));
		FuenteDinamica fuenteDinamica = new FuenteDinamica();
		List<Multimedia> multimedia = new LinkedList<Multimedia>();

		Hecho h1 = new Hecho("Incendio en Córdoba",
				"Se detecta foco en zona norte",
				"A",
				-31.4f,
				-64.2f,
				LocalDate.of(2025, 6, 12),
				fuenteDinamica,
				admin01,
				false,
				multimedia
		);

		Hecho h2 = new Hecho("Incendio en BSAs",
				"Se detecta foco en zona norte",
				"B",
				-31.4f,
				-64.2f,
				LocalDate.of(2025, 6, 12),
				fuenteDinamica,
				admin01,
				false,
				multimedia
		);

		Agregador agregador = new Agregador(List.of(fuenteDinamica));
		Coleccion coleccion = new Coleccion("Incendios 2025", "incendios",null,null, agregador);
		List<Hecho> hechosMostrados = coleccion.filtrarPorCriterios(
				List.of(),  // sin filtros adicionales
				List.of()
		);

		/* ---------- 3. Verificaciones ---------- */
		assertThat(hechosMostrados)
				.containsExactlyInAnyOrder(h1, h2)   // se muestran los dos
				.allMatch(h -> !h.getEliminado());
	}
	
	
	
	
//TODO: 4 Como persona visualizadora, deseo navegar los hechos disponibles de una colección, aplicando filtros.
@Test
public void navegarHechosAplicandoFiltros(){
	Perfil admin01 = new Perfil("Juan", "Perez", 30);
	Usuario admin = new Usuario("admin1@frba.utn.edu.ar", "algo", admin01, List.of(Rol.VISUALIZADOR));
	FuenteDinamica fuenteDinamica = new FuenteDinamica();
	List<Multimedia> multimedia = new LinkedList<Multimedia>();

	Hecho h1 = new Hecho("Incendio en Córdoba",
			"Se detecta foco en zona norte",
			"A",
			-31.4f,
			-64.2f,
			LocalDate.of(2025, 6, 12),
			fuenteDinamica,
			admin01,
			false,
			multimedia
	);
	Hecho h2 = new Hecho("Incendio en Córdoba",
			"Se detecta foco en zona norte",
			"A",
			-31.4f,
			-64.2f,
			LocalDate.of(2025, 6, 12),
			fuenteDinamica,
			admin01,
			false,
			multimedia
	);

	fuenteDinamica.agregarHecho(
			"Incendio en BSAs",
			"Se detecta foco en zona sur",
			"B",
			-31.4f,-64.2f,
			LocalDate.of(2025, 6, 12),
			admin01,
			false,
			fuenteDinamica, null
	);
	fuenteDinamica.agregarHecho(
			"Incendio en Cordoba",
			"Se detecta foco en zona norte",
			"A",
			-31.4f,-64.2f,
			LocalDate.of(2025, 6, 12),
			admin01,
			false,
			fuenteDinamica, null
	);
	Agregador agregador = new Agregador(List.of(fuenteDinamica));
	Coleccion coleccion = new Coleccion("Incendios 2025", "incendios",null,null, agregador);
	List<Criterio> filtrosPertenencia = List.of(
			new CriterioCategoria("Incendio"),
			new CriterioFecha(LocalDate.of(2025, 6, 10),
					LocalDate.of(2025, 6, 12))
	);

	// ningún criterio extra de “no pertenencia”
	List<Criterio> filtrosNoPertenencia = List.of();

	/* ---------- 3. Acción: navegar con filtros ---------- */
	List<Hecho> resultado = coleccion.filtrarPorCriterios(
			filtrosPertenencia, filtrosNoPertenencia);

	/* ---------- 4. Verificaciones ---------- */
	assertThat(resultado)
			.containsExactly(h1)   // solo el hecho que cumple ambos filtros
			.doesNotContain(h2);

}


	// TODO: 5 Como persona contribuyente, deseo poder solicitar la eliminación de un hecho.

public void solicitarEliminacionHecho(){
		Hecho unHecho= new Hecho("incendio", "desc",null,null,null,null,null,null,null,null);
		Perfil contrib01 = new Perfil("Juan", "Perez", 30);
		Usuario admin = new Usuario("admin1@frba.utn.edu.ar", "algo", contrib01, List.of(Rol.CONTRIBUYENTE, Rol.VISUALIZADOR));

	if (!contrib01.tieneRol(Rol.CONTRIBUYENTE)) {
		throw new IllegalStateException("El usuario no tiene rol de CONTRIBUYENTE.");
	}

		SolicitudEliminacion solicitudEliminacion1 = new SolicitudEliminacion(unHecho, "Este hecho no me gusta");
}

	//TODO : 6 Como persona administradora, deseo poder aceptar o rechazar la solicitud de eliminación de un hecho.

	public void manejarSolicitudEliminacion(){

	}
	@Test
	void contextLoads() {
	}

	@Test
	public void testFuenteDemo(){
		FuenteDeDatos fuenteDemo = new FuenteProxy("", new HechoParser() {
			@Override
			public ArrayList<Hecho> parsearHecho(String path) {
				return ArrayList.of();
			}
		});
	}


//Como persona contribuyente, deseo poder crear un hecho a partir de una fuente dinámica.
	@Test
	public void agregarHechoAFuente(){
		Perfil perfilTest = new Perfil("Test","Prueba",99);
		FuenteDinamica fuenteDinamica = new FuenteDinamica();
		List<Multimedia> multimedia = new LinkedList<Multimedia>();

		Hecho nuevo = new Hecho("Incendio en Córdoba",
				"Se detecta foco en zona norte",
				"A",
				-31.4f,
				-64.2f,
				LocalDate.of(2025, 6, 12),
				fuenteDinamica,
				perfilTest,
				false,
				multimedia
		);

		fuenteDinamica.agregarHecho(
						"Incendio en Córdoba",
						"Se detecta foco en zona norte",
						"A",
						-31.4f,-64.2f,
						LocalDate.of(2025, 6, 12),
						perfilTest,
						false,
						fuenteDinamica, null
				);

		assertThat(fuenteDinamica.getHechos())     // se agregó a la fuente
				.containsExactly(nuevo);
		assertThat(nuevo.getAutor()).isNull();          // anónimo
		assertThat(nuevo.getFechaCarga()).isToday();

	}
}