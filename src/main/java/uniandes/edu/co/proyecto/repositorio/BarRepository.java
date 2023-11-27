package uniandes.edu.co.proyecto.repositorio;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import uniandes.edu.co.proyecto.Modelo.Bar;

public interface BarRepository extends MongoRepository<Bar, String>{
    List <Bar> findByNombre(String nombre);
}
