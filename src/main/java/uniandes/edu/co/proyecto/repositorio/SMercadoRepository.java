package uniandes.edu.co.proyecto.repositorio;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import uniandes.edu.co.proyecto.Modelo.SMercado;

public interface SMercadoRepository extends MongoRepository<SMercado, String>{
    List<SMercado> findByNombre(String nombre);
}

