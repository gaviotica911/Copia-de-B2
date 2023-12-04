package uniandes.edu.co.proyecto.Modelo;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;


@Document(collection = "reservas")

public  class Reserva {
    @Id
    private String id;
    
    @Field("fechaEntrada")
     @DateTimeFormat(pattern = "yyyy-MM-dd") 
    private Date fechaentrada;
     @Field("fechaSalida")
     @DateTimeFormat(pattern = "yyyy-MM-dd") 
    private Date fechasalida;
     @Field("numPersonas")
    private Integer numpersonas;
     @Field("estado")
    private boolean estado;
     @Field("precioReserva")
    private double precioreserva;
    @Field ("docUsuario")
    private String docUsuario;
    @Field("habitaciones")
    private List<String> habitaciones;
 
   

    public Reserva(Date fechaentrada, Date fechasalida, Integer numpersonas, boolean estado, double precioreserva,
            String docUsuario, List<String>  habitaciones) {
        this.fechaentrada = fechaentrada;
        this.fechasalida = fechasalida;
        this.numpersonas = numpersonas;
        this.estado = estado;
        this.precioreserva = precioreserva;
        this.docUsuario = docUsuario;
        this.habitaciones = habitaciones;

     
    }
    public Reserva(){}

   

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFechaentrada() {
        return fechaentrada;
    }

    public void setFechaentrada(Date fechaentrada) {
        this.fechaentrada = fechaentrada;
    }

    public Date getFechasalida() {
        return fechasalida;
    }

    public void setFechasalida(Date fechasalida) {
        this.fechasalida = fechasalida;
    }

    public Integer getNumpersonas() {
        return numpersonas;
    }

    public void setNumpersonas(Integer numpersonas) {
        this.numpersonas = numpersonas;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public double getPrecioreserva() {
        return precioreserva;
    }

    public void setPrecioreserva(double precioreserva) {
        this.precioreserva = precioreserva;
    }

    public String getDocUsuario() {
        return docUsuario;
    }

    public void setDocUsuario(String docUsuario) {
        this.docUsuario = docUsuario;
    }
    public List<String>  getHabitaciones() {
        return habitaciones;
    }
    public void setHabitaciones (List<String>  habitaciones) {
        this.habitaciones = habitaciones;
    }

    

   

    
    
}
