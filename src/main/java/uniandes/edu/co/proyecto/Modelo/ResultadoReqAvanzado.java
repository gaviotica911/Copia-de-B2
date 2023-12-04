package uniandes.edu.co.proyecto.Modelo;

import java.util.List;

public class ResultadoReqAvanzado {
    private String docUsuario;
    private List<String> fechas;

    public ResultadoReqAvanzado(String docUsuario, List<String> fechas) {
        this.docUsuario = docUsuario;
        this.fechas = fechas;
    }

    public String getDocUsuario() {
        return docUsuario;
    }

    public void setDocUsuario(String docUsuario) {
        this.docUsuario = docUsuario;
    }

    public List<String> getFechas() {
        return fechas;
    }

    public void setFechas(List<String> fechas) {
        this.fechas = fechas;
    }

    

    
}
