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
import uniandes.edu.co.proyecto.Modelo.SMercado;
import uniandes.edu.co.proyecto.repositorio.SMercadoRepository;

@Controller
public class SMercadoController {
    @Autowired
    private SMercadoRepository sMercadoRepository;

    @GetMapping("/mercados")
    public String mercados(Model model){
        model.addAttribute("mercados", sMercadoRepository.findAll());
        return "SMercados";
    }

    @GetMapping("/mercados/new")
    public String mercadoForm(Model model){
        model.addAttribute("mercado", new SMercado());
        return "SMercadoNuevo";       
    }

    @PostMapping("/mercados/new/save")
    public String mercadoGuardar(@ModelAttribute SMercado mercado){
        sMercadoRepository.save(mercado);
        return "redirect:/mercados";
    }

    @GetMapping("/mercados/{id}/edit")
    public String mercadoEditarForm(@PathVariable("id") String id, Model model){
        Optional<SMercado> mercadoExistente= sMercadoRepository.findById(id);
        if (mercadoExistente.isPresent()) {
            SMercado mercado = mercadoExistente.get();
            model.addAttribute("mercado", mercado);
        }
        return "SMercadoEditar";
        
    }

    @PostMapping("/mercados/{id}/edit/save")
    public String editar(@PathVariable("id") String id, @ModelAttribute SMercado mercado){
        sMercadoRepository.save(mercado);
        return "redirect:/mercados";
    }

    @GetMapping("/mercados/{id}/delete")
    public String mercadoBorrar(@PathVariable("id") String id){
        Optional<SMercado> mercadoExistente= sMercadoRepository.findById(id);
        if (mercadoExistente.isPresent()) {
            sMercadoRepository.deleteById(id);
        }
        return "redirect:/mercados";
    }

    //Controladores para el manejo de Procuctos embebidos

    @GetMapping("/addProducto")
    public String anadirProducto(@RequestParam(name = "nombre", required= false) String nombre, Model model){
        model.addAttribute("nombreSMercado", nombre);   
        model.addAttribute("producto", new ProductosEmbedded());
        return "addProductoForm";
    }

    @PostMapping("/addProductoSave")
    public String añadirBebidaSave(@RequestParam("nombreSMercado") String nombreSMercado,
    @ModelAttribute("producto") ProductosEmbedded product){

        // Creamos una nueva bebida utilizando los datos del formulario
        ProductosEmbedded nuevoProducto = new ProductosEmbedded(
            product.getNombre(),
            product.getDescripcion()
        );

        //Buscamos las mercados con ese nombre
        List<SMercado> products = sMercadoRepository.findByNombre(nombreSMercado);

        //Añadimos ese producto a todas las mercados con ese nombre
        for (SMercado mercado:products){
            if (mercado.getProductos() == null){
                List<ProductosEmbedded> emptyList = new ArrayList<>();
                mercado.setProductos(emptyList);
            }
            mercado.addProducto(nuevoProducto);

            //Persistemos la modificacion en la base de datos
            sMercadoRepository.save(mercado);
        }
        
        return "redirect:/mercados";

    }
}

