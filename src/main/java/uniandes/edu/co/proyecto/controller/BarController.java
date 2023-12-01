package uniandes.edu.co.proyecto.controller;

import java.util.ArrayList;
import java.util.Collections;
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

import uniandes.edu.co.proyecto.Modelo.Bar;
import uniandes.edu.co.proyecto.Modelo.PlatosYBebidasEmbedded;
import uniandes.edu.co.proyecto.repositorio.BarRepository;

@Controller
public class BarController {

    @Autowired
    private BarRepository barRepository;

    @GetMapping("/bares")
    public String bares(Model model){
        model.addAttribute("bares", barRepository.findAll());
        return "bar";
    }

    @GetMapping("/bares/new")
    public String barForm(Model model){
        model.addAttribute("bar", new Bar());
        return "barNuevo";       
    }

    @GetMapping("/bares/{id}/edit")
    public String barEditarForm(@PathVariable("id") String id, Model model){
        Optional<Bar> barExistente= barRepository.findById(id);
        if (barExistente.isPresent()) {
            Bar bar = barExistente.get();
            model.addAttribute("bar", bar);
        }
        return "BarEditar";
        
    }

    @PostMapping("/bares/{id}/edit/save")
    public String editar(@PathVariable("id") String id, @ModelAttribute Bar bar){
        barRepository.save(bar);
        return "redirect:/bares";
    }

    @GetMapping("/bares/{id}/delete")
    public String eliminarBar(@PathVariable String id){
        Optional<Bar> barExistente = barRepository.findById(id);
        if (barExistente.isPresent()) {
            barRepository.deleteById(id);
        }
        return "redirect:/bares";
    }
    
    //Controladores para el manejo de las bebidas y platos embebidos

    @PostMapping("/bares/new/save")
    public String crearBarBebida(@ModelAttribute("bar") Bar bar) {

        // Guardamos el nuevo tipo de bebida
        barRepository.save(bar);
        return "redirect:/bares";
    }

    @GetMapping("/addBebida")
    public String añadirBebida(@RequestParam(name = "nombre", required = false) String nombre, Model model){
        model.addAttribute("nombreTipoBebida", nombre);
        model.addAttribute("bebida", new PlatosYBebidasEmbedded());


        return "addBebidaForm";
    }

    @PostMapping("/addBebidaSave")
    public String añadirBebidaSave(@RequestParam("nombreTipoBebida") String nombreTipoBebida,
    @ModelAttribute("bebida") PlatosYBebidasEmbedded beb){

        // Creamos una nueva bebida utilizando los datos del formulario
        PlatosYBebidasEmbedded nuevaBebida = new PlatosYBebidasEmbedded(
            beb.getNombre(),
            beb.getDescripcion()
        );

        //Buscamos los tipos de bebida con ese nombre
        List<Bar> bebs = barRepository.findByNombre(nombreTipoBebida);

        //Añadimos esa bebida a todos los tipos de bebidas con ese nombre
        for (Bar tipoBebida:bebs){
            if (tipoBebida.getPlatosybebidas() == null){
                List<PlatosYBebidasEmbedded> emptyList = new ArrayList<>();
                tipoBebida.setPlatosybebidas(emptyList);
            }
            tipoBebida.addPlatoYBebida(nuevaBebida);

            //Persistemos la modificacion en la base de datos
            barRepository.save(tipoBebida);
        }
        
        return "redirect:/bares";

    }
}
