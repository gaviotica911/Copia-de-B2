package uniandes.edu.co.proyecto.repositorio;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import uniandes.edu.co.proyecto.Modelo.Tienda;

public interface TiendaRepository extends MongoRepository<Tienda, String>{
    List<Tienda> findByNombre(String nombre);
}
