package uniandes.edu.co.proyecto.Modelo;

public class ResultadoReq2 {
    private String habitacionId;
    private double totalConsumos;

    public ResultadoReq2(String habitacionId, double totalConsumos) {
        this.habitacionId = habitacionId;
        this.totalConsumos = totalConsumos;
    }

    public String getHabitacionId() {
        return habitacionId;
    }

    public void setHabitacionId(String habitacionId) {
        this.habitacionId = habitacionId;
    }

    public double getTotalConsumos() {
        return totalConsumos;
    }

    public void setTotalConsumos(double totalConsumos) {
        this.totalConsumos = totalConsumos;
    }

    
}
