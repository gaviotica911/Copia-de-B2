package uniandes.edu.co.proyecto.Modelo;

public class ResultadoReq3 {
    private  String consumo;
    private double precio;

    public ResultadoReq3(String consumo, double precio) {
        this.consumo = consumo;
        this.precio = precio;
    }
    
    public String getConsumo() {
        return consumo;
    }
    public void setConsumo(String consumo) {
        this.consumo = consumo;
    }
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    


    
}
