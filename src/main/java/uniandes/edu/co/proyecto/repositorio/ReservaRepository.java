
package uniandes.edu.co.proyecto.repositorio;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

import uniandes.edu.co.proyecto.Modelo.Reserva;


public interface ReservaRepository extends MongoRepository<Reserva, String> {
    
    List<Reserva> findByDocUsuario(String nombre);

    //
}