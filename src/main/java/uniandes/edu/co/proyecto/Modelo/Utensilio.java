package uniandes.edu.co.proyecto.Modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Servicios")

public class Utensilio {
    @Id
    private Integer id;
    @Field("descripcion")
    private String descripcion;
    @Field("tipo")
    private String tipo;
    @Field("duracion")
    private Integer duracion;

    public Utensilio(Integer id, String descripcion, String tipo, Integer duracion) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.duracion = duracion;
    }

    public Utensilio() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    
    
}
