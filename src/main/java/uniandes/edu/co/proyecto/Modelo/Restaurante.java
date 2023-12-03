package uniandes.edu.co.proyecto.Modelo;

import java.time.LocalTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.List;

@Document(collection = "Rests")

public class Restaurante {
    @Id
    private String id;
    @Field("nombre")
    private String nombre;
    @Field("tipo")
    private String tipo;
    @Field("horarioapertura")
    private LocalTime horarioapertura;
    @Field("horariocierre")
    private LocalTime horariocierre;
    @Field("capacidad")
    private Integer capacidad;
    @Field("platosybebidas")
    private List<PlatosYBebidasEmbedded> platosybebidas;

    public Restaurante(String id, String nombre, LocalTime horarioapertura, LocalTime horariocierre, Integer capacidad,
            List<PlatosYBebidasEmbedded> platosybebidas) {
        this.id = id;
        this.nombre = nombre;
        this.horarioapertura = horarioapertura;
        this.horariocierre = horariocierre;
        this.capacidad = capacidad;
        this.platosybebidas = platosybebidas;
    }

    public Restaurante() {}

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public List<PlatosYBebidasEmbedded> getPlatosybebidas() {
        return platosybebidas;
    }

    public void setPlatosybebidas(List<PlatosYBebidasEmbedded> platosybebidas) {
        this.platosybebidas = platosybebidas;
    }

    public void addPlatoYBebida(PlatosYBebidasEmbedded platoYBebida){
        this.platosybebidas.add(platoYBebida);
    }


}

