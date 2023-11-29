package uniandes.edu.co.proyecto.Modelo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalTime;
import java.util.List;

@Document(collection = "Tiendas")
public class Tienda {
    @Id
    private String id;
    @Field("nombre")
    private String nombre;
    @Field("horarioapertura")
    private LocalTime horarioapertura;
    @Field("horariocierre")
    private LocalTime horariocierre;
    @Field("capacidad")
    private Integer capacidad;
    @Field("productos")
    private List<Productos> productos;

    public Tienda(String id, String nombre, LocalTime horarioapertura, LocalTime horariocierre, Integer capacidad,
            List<Productos> productos) {
        this.id = id;
        this.nombre = nombre;
        this.horarioapertura = horarioapertura;
        this.horariocierre = horariocierre;
        this.capacidad = capacidad;
        this.productos = productos;
    }

    public Tienda() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalTime getHorarioapertura() {
        return horarioapertura;
    }

    public void setHorarioapertura(LocalTime horarioapertura) {
        this.horarioapertura = horarioapertura;
    }

    public LocalTime getHorariocierre() {
        return horariocierre;
    }

    public void setHorariocierre(LocalTime horariocierre) {
        this.horariocierre = horariocierre;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public List<Productos> getProductos() {
        return productos;
    }

    public void setProductos(List<Productos> productos) {
        this.productos = productos;
    }

    

}
