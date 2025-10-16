package Agregador.persistencia;
import Agregador.DTO.FiltrosHechosDTO;
import Agregador.business.Consenso.Consenso;
import Agregador.business.Consenso.ModosDeNavegacion;
import Agregador.business.Hechos.Hecho;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RepositorioConsenso extends JpaRepository<Consenso, BigInteger> {
  Optional<Consenso> findByNombreTipo(String nombreTipo);
}