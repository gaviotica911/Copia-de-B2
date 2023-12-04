package uniandes.edu.co.proyecto.Modelo;

public class ResultadoReq1 {
    private String habitacionId;
    private double porcentajeOcupacion;

    public ResultadoReq1(String habitacionId, double porcentajeOcupacion) {
        this.habitacionId = habitacionId;
        this.porcentajeOcupacion = porcentajeOcupacion;
    }

    public ResultadoReq1(){}
    
    public String getHabitacionId() {
        return habitacionId;
    }

    public void setHabitacionId(String habitacionId) {
        this.habitacionId = habitacionId;
    }

    public double getporcentajeOcupacion() {
        return porcentajeOcupacion;
    }

    public void setporcentajeOcupacion(double porcentajeOcupacion) {
        this.porcentajeOcupacion = porcentajeOcupacion;
    }

    
}
