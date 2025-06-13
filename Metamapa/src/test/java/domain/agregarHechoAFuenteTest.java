package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.business.FuentesDeDatos.FuenteDinamica;
import domain.business.Usuarios.Perfil;
import domain.business.incidencias.Hecho;
import domain.business.incidencias.Multimedia;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

@SpringBootTest
class agregarHechoAFuenteTest {


  @Test
  public void test(){
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

