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

import uniandes.edu.co.proyecto.Modelo.Restaurante;
import uniandes.edu.co.proyecto.Modelo.PlatosYBebidasEmbedded;
import uniandes.edu.co.proyecto.repositorio.RestauranteRepository;

@Controller
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping("/restaurantes")
    public String restaurantes(Model model){
        model.addAttribute("restaurantes", restauranteRepository.findAll());
        return "restaurante";
    }

    @GetMapping("/restaurantes/new")
    public String restauranteForm(Model model){
        model.addAttribute("restaurante", new Restaurante());
        return "restauranteNuevo";       
    }

    @GetMapping("/restaurantes/{id}/edit")
    public String restauranteEditarForm(@PathVariable("id") String id, Model model){
        Optional<Restaurante> restauranteExistente= restauranteRepository.findById(id);
        if (restauranteExistente.isPresent()) {
            Restaurante restaurante = restauranteExistente.get();
            model.addAttribute("restaurante", restaurante);
        }
        return "RestauranteEditar";
        
    }

    @PostMapping("/restaurantes/{id}/edit/save")
    public String editar(@PathVariable("id") String id, @ModelAttribute Restaurante restaurante){
        restauranteRepository.save(restaurante);
        return "redirect:/restaurantes";
    }

    @GetMapping("/restaurantes/{id}/delete")
    public String eliminarRestaurante(@PathVariable String id){
        Optional<Restaurante> restauranteExistente = restauranteRepository.findById(id);
        if (restauranteExistente.isPresent()) {
            restauranteRepository.deleteById(id);
        }
        return "redirect:/restaurantes";
    }
    
    //Controladores para el manejo de las bebidas y platos embebidos

    @PostMapping("/restaurantes/new/save")
    public String crearRestauranteBebida(@ModelAttribute("restaurante") Restaurante restaurante) {

        // Guardamos el nuevo tipo de bebida
        restauranteRepository.save(restaurante);
        return "redirect:/restaurantes";
    }

    @GetMapping("/addPlatoYBebida")
    public String añadirBebida(@RequestParam(name = "nombre", required = false) String nombre, Model model){
        model.addAttribute("nombreTipoPlatoYBebida", nombre);
        model.addAttribute("platoybebida", new PlatosYBebidasEmbedded());


        return "addPlatoYBebidaForm";
    }

    @PostMapping("/addPlatoYBebidaSave")
    public String añadirBebidaSave(@RequestParam("nombreTipoPlatoYBebida") String nombreTipoPlatoYBebida,
    @ModelAttribute("platoybebida") PlatosYBebidasEmbedded platoybebida){

        // Creamos una nueva bebida utilizando los datos del formulario
        PlatosYBebidasEmbedded nuevoPlatoYBebida = new PlatosYBebidasEmbedded(
            platoybebida.getNombre(),
            platoybebida.getDescripcion()
        );

        //Buscamos los tipos de bebida con ese nombre
        List<Restaurante> platosybebidas = restauranteRepository.findByNombre(nombreTipoPlatoYBebida);

        //Añadimos esa bebida a todos los tipos de bebidas con ese nombre
        for (Restaurante tipoPlatoYBebida:platosybebidas){
            if (tipoPlatoYBebida.getPlatosybebidas() == null){
                List<PlatosYBebidasEmbedded> emptyList = new ArrayList<>();
                tipoPlatoYBebida.setPlatosybebidas(emptyList);
            }
            tipoPlatoYBebida.addPlatoYBebida(nuevoPlatoYBebida);

            //Persistemos la modificacion en la base de datos
            restauranteRepository.save(tipoPlatoYBebida);
        }
        
        return "redirect:/restaurantes";

    }
}

