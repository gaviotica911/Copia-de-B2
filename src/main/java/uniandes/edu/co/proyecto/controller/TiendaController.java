package uniandes.edu.co.proyecto.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uniandes.edu.co.proyecto.Modelo.ProductosEmbedded;
import uniandes.edu.co.proyecto.Modelo.Tienda;
import uniandes.edu.co.proyecto.repositorio.TiendaRepository;

@Controller
public class TiendaController {
    @Autowired
    private TiendaRepository tiendaRepository;

    @GetMapping("/tiendas")
    public String tiendas(Model model){
        model.addAttribute("tiendas", tiendaRepository.findAll());
        return "Tiendas";
    }

    @GetMapping("/tiendas/new")
    public String tiendaForm(Model model){
        model.addAttribute("tienda", new Tienda());
        return "TiendaNuevo";       
    }

    @PostMapping("/tiendas/new/save")
    public String tiendaGuardar(@ModelAttribute Tienda tienda){
        tiendaRepository.save(tienda);
        return "redirect:/tiendas";
    }

    @GetMapping("/tiendas/{id}/edit")
    public String tiendaEditarForm(@PathVariable("id") String id, Model model){
        Optional<Tienda> tiendaExistente= tiendaRepository.findById(id);
        if (tiendaExistente.isPresent()) {
            Tienda tienda = tiendaExistente.get();
            model.addAttribute("tienda", tienda);
        }
        return "TiendaEditar";
        
    }

    @PostMapping("/tiendas/{id}/edit/save")
    public String editar(@PathVariable("id") String id, @ModelAttribute Tienda tienda){
        tiendaRepository.save(tienda);
        return "redirect:/tiendas";
    }

    @GetMapping("/tiendas/{id}/delete")
    public String tiendaBorrar(@PathVariable("id") String id){
        Optional<Tienda> tiendaExistente= tiendaRepository.findById(id);
        if (tiendaExistente.isPresent()) {
            tiendaRepository.deleteById(id);
        }
        return "redirect:/tiendas";
    }

    //Controladores para el manejo de Procuctos embebidos

    @GetMapping("/addProductoT")
    public String anadirProducto(@RequestParam(name = "nombre", required= false) String nombre, Model model){
        model.addAttribute("nombreTienda", nombre);   
        model.addAttribute("producto", new ProductosEmbedded());
        return "addProductoForm";
    }

    @PostMapping("/addProductoSaveT")
    public String añadirBebidaSave(@RequestParam("nombreTienda") String nombreTienda,
    @ModelAttribute("producto") ProductosEmbedded product){

        // Creamos una nueva bebida utilizando los datos del formulario
        ProductosEmbedded nuevoProducto = new ProductosEmbedded(
            product.getNombre(),
            product.getDescripcion()
        );

        //Buscamos las tiendas con ese nombre
        List<Tienda> products = tiendaRepository.findByNombre(nombreTienda);

        //Añadimos ese producto a todas las tiendas con ese nombre
        for (Tienda tienda:products){
            if (tienda.getProductos() == null){
                List<ProductosEmbedded> emptyList = new ArrayList<>();
                tienda.setProductos(emptyList);
            }
            tienda.addProducto(nuevoProducto);

            //Persistemos la modificacion en la base de datos
            tiendaRepository.save(tienda);
        }
        
        return "redirect:/tiendas";

    }
}
