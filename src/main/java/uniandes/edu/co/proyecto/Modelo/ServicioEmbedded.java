package uniandes.edu.co.proyecto.Modelo;

public class ServicioEmbedded {
    private String descripcion;
    private String tipo;
    private Integer duracion;

    public ServicioEmbedded(String descripcion, String tipo, Integer duracion) {
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.duracion = duracion;
    }

    public ServicioEmbedded(){}

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
