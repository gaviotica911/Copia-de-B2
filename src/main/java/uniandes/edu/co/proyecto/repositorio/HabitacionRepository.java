package uniandes.edu.co.proyecto.repositorio;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

import uniandes.edu.co.proyecto.Modelo.Habitacion;

public interface HabitacionRepository extends MongoRepository<Habitacion, String> {
    //findby tipoHabitacion?
    //findby capacidad?
}
