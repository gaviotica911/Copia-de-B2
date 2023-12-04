package uniandes.edu.co.proyecto.Modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "habitaciones")
public class Habitacion {
    @Id
    private String id;
    @Field("capacidad")
    private Integer capacidad;
    @Field("tipo")
    private List<TipoHabitacionEmbedded> tipo;

    public Habitacion(Integer capacidad, List<TipoHabitacionEmbedded> tipo) {
        this.capacidad = capacidad;
        this.tipo = tipo;
    }

    public Habitacion(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public List<TipoHabitacionEmbedded> getTipoHabitacion() {
        return tipo;
    }

    public void setTipoHabitacion(List<TipoHabitacionEmbedded> tipo) {
        this.tipo = tipo;
    }

    public void addTipoHabitacion(TipoHabitacionEmbedded tipoHab){
        this.tipo.add(tipoHab);
    }
}
