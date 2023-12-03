package uniandes.edu.co.proyecto.repositorio;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

import uniandes.edu.co.proyecto.Modelo.Consumo;

public interface ConsumoRepository extends MongoRepository<Consumo, String>{
    
    List<Consumo> findByFecha(String nombre);
    List<Consumo> findByPrecio(double precio);
}
