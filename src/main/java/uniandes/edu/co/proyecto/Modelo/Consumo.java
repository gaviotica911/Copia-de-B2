package uniandes.edu.co.proyecto.Modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Document(collection = "consumos")
public class Consumo {
    @Id
    private String id;
    
    @Field("fecha")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;

    @Field("precio")
    private double precio;

    @Field("productos")
    private List<ProductosEmbedded> productos;

    @Field("platosybebidas")
    private List<PlatosYBebidasEmbedded> platosybebidas;

    @Field("servicios")
    private List<ServicioEmbedded> servicios;

    @Field("reservasid")
    private List<String> reservasid;

    public Consumo(Date fecha, double precio, List<ProductosEmbedded> productos,
            List<PlatosYBebidasEmbedded> platosybebidas, List<ServicioEmbedded> servicios, List<String> reservasid) {
        this.fecha = fecha;
        this.precio = precio;
        this.productos = productos;
        this.platosybebidas = platosybebidas;
        this.servicios = servicios;
        this.reservasid = reservasid;
    }

    public Consumo() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public List<ProductosEmbedded> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductosEmbedded> productos) {
        this.productos = productos;
    }

    public List<PlatosYBebidasEmbedded> getPlatosybebidas() {
        return platosybebidas;
    }

    public void setPlatosybebidas(List<PlatosYBebidasEmbedded> platosybebidas) {
        this.platosybebidas = platosybebidas;
    }

    public List<ServicioEmbedded> getServicios() {
        return servicios;
    }

    public void setServicios(List<ServicioEmbedded> servicios) {
        this.servicios = servicios;
    }

    public List<String> getReservasid() {
        return reservasid;
    }

    public void setReservasid(List<String> reservasid) {
        this.reservasid = reservasid;
    }

    public void addProducto(ProductosEmbedded producto){
        this.productos.add(producto);
    }   

    public void addServicio(ServicioEmbedded servicio) {
        this.servicios.add(servicio);
    }
    
    public void addPlatoYBebida(PlatosYBebidasEmbedded platoYBebida){
        this.platosybebidas.add(platoYBebida);
    }
    
}
