package uniandes.edu.co.proyecto.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public String reservas(Model model){
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
    public String barEditarForm(@PathVariable("id") String id, @ModelAttribute Bar bar){
        Optional<Bar> barExistente= barRepository.findById(id);
        if (barExistente.isPresent()) {
            barRepository.save(bar);
            return "barEditar";
        }
        else {
            return "redirect:/bares";
        }
    }

    @DeleteMapping("/bares/{id}/delete")
    public ResponseEntity<String> barEliminar(@PathVariable("id") String id){
        try {
            barRepository.deleteById(id);
            return new ResponseEntity<>("El bar ha sido eliminado", HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>("El bar no ha sido eliminado", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
