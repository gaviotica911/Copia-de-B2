package uniandes.edu.co.proyecto.repositorio;
import org.springframework.data.mongodb.repository.MongoRepository;
import uniandes.edu.co.proyecto.Modelo.Utensilio;
import java.util.List;

public interface UtensilioRepository extends MongoRepository<Utensilio, String>{
    List<Utensilio> findByDescripcion(String descripcion);
}
