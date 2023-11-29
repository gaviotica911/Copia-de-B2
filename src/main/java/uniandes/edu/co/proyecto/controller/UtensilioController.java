package uniandes.edu.co.proyecto.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import uniandes.edu.co.proyecto.Modelo.Utensilio;
import uniandes.edu.co.proyecto.repositorio.UtensilioRepository;

@Controller
public class UtensilioController {
    @Autowired
    private UtensilioRepository utensilioRepository;

    @GetMapping("/utensilios")
    public String utensilios(Model model){
        model.addAttribute("utensilios", utensilioRepository.findAll());
        return "Utensilios";
    }

    @GetMapping("/utensilios/new")
    public String utensilioForm(Model model){
        model.addAttribute("utensilio", new Utensilio());
        return "utensilioNuevo";       
    }

    @PostMapping("/utensilios/new/save")
    public String utensilioGuardar(@ModelAttribute Utensilio utensilio){
        utensilioRepository.save(utensilio);
        return "redirect:/utensilios";
    }
    
    @GetMapping("/utensilios/{id}/edit")
    public String utensilioEditarForm(@PathVariable("id") String id, Model model){
        Optional<Utensilio> utensilioExistente= utensilioRepository.findById(id);
        if (utensilioExistente.isPresent()) {
            Utensilio utensilio = utensilioExistente.get();
            model.addAttribute("utensilio", utensilio);
        }
        return "utensilioEditar";
    }

    @PostMapping("/utensilios/{id}/edit/save") 
    public String editar(@PathVariable("id") String id, @ModelAttribute Utensilio utensilio){
        utensilioRepository.save(utensilio);
        return "redirect:/utensilios";
    }

    @GetMapping("/utensilios/{id}/delete")
    public String eliminarUtensilio(@PathVariable String id){
        Optional<Utensilio> utensilioExistente= utensilioRepository.findById(id);
        if (utensilioExistente.isPresent()) {
            utensilioRepository.deleteById(id);
        }
        return "redirect:/utensilios";
    }
    
}
