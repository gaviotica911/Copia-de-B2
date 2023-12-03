package uniandes.edu.co.proyecto.Modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "habitacion")
public class Habitacion {
    @Id
    private String id;
    @Field("capacidad")
    private Integer capacidad;
    @Field("tipoid")
    private TipoHabitacionEmbedded tipoHabitacion;

    public Habitacion(Integer capacidad, TipoHabitacionEmbedded tipoHabitacion) {
        this.capacidad = capacidad;
        this.tipoHabitacion = tipoHabitacion;
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

    public TipoHabitacionEmbedded getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(TipoHabitacionEmbedded tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    
}
