package uniandes.edu.co.proyecto.repositorio;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import uniandes.edu.co.proyecto.Modelo.Restaurante;

public interface RestauranteRepository extends MongoRepository<Restaurante, String>{
    List <Restaurante> findByNombre(String nombre);
}
