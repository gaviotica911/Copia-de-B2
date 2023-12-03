package uniandes.edu.co.proyecto.Modelo;


public  class TipoHabitacionEmbedded {

    private String tipo;
    private String descripcion;
    
    public TipoHabitacionEmbedded(){}

    public TipoHabitacionEmbedded(String tipo, String descripcion) {
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    } 
    
    

}
