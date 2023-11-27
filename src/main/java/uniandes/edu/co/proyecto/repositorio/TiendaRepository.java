package uniandes.edu.co.proyecto.repositorio;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import uniandes.edu.co.proyecto.Modelo.Tienda;

public interface TiendaRepository extends MongoRepository<Tienda, Integer>{
    List<Tienda> findByNombre(String nombre);
}
