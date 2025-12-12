package FuenteMetamapa.persistencia;
import FuenteMetamapa.business.FuentesDeDatos.*;
import java.util.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RepositorioFuentes extends JpaRepository<FuenteMetamapa,Integer>{
}