package uniandes.edu.co.proyecto.repositorio;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import uniandes.edu.co.proyecto.Modelo.TipoHabitacion;

public interface TipoHabitacionRepository extends MongoRepository<TipoHabitacion, Integer>{
    // List<TipoHabitacion> findByNombre(String nombre);
}
