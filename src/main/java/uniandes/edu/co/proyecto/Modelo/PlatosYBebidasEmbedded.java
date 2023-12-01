package uniandes.edu.co.proyecto.Modelo;

public class PlatosYBebidasEmbedded {

    private String nombre;
    private String descripcion;

    public PlatosYBebidasEmbedded(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public PlatosYBebidasEmbedded() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
}
