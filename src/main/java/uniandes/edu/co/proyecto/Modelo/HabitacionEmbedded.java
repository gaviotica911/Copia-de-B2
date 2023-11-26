package uniandes.edu.co.proyecto.Modelo;




public  class HabitacionEmbedded {
    private Integer id;
    private String capacidad;
    public HabitacionEmbedded(Integer id, String capacidad) {
        this.id = id;
        this.capacidad = capacidad;
    }
    public HabitacionEmbedded() 
    {} 

    public Integer getId() {
        return id;
    }   
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCapacidad() {
        return capacidad;
    }
    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }
    


}
