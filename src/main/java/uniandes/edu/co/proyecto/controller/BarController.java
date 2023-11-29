package uniandes.edu.co.proyecto.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import uniandes.edu.co.proyecto.Modelo.Bar;
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

    @PostMapping("/bares/new/save")
    public String barGuardar(@ModelAttribute Bar bar){
        barRepository.save(bar);
        return "redirect:/bares";
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
    
}
