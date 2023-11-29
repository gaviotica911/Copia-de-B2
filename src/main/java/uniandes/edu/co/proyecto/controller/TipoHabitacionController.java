package uniandes.edu.co.proyecto.controller;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import uniandes.edu.co.proyecto.Modelo.TipoHabitacion;

import uniandes.edu.co.proyecto.repositorio.TipoHabitacionRepository;

@Controller
public class TipoHabitacionController {
    @Autowired
    private TipoHabitacionRepository tipoHabitacionRepository;
    

    @GetMapping("/tiposHab")
    public String tipoHabitaciones(Model model){
        model.addAttribute("tipoHabitaciones", tipoHabitacionRepository.findAll());
        return "TipoHabitacion";
    }

    @GetMapping("/tiposHab/new")
    public String tipoHabitacionForm(Model model){
        model.addAttribute("tipoHabitacion", new TipoHabitacion());
        return "TipoHabitacionNuevo";       
    }

    @PostMapping("/tiposHab/new/save")
    public String tipoHabitacionGuardar(@ModelAttribute TipoHabitacion tipoHabitacion){
        tipoHabitacionRepository.save(tipoHabitacion);
        return "redirect:/tiposHab";
    }

    @GetMapping("/tiposHab/{id}/edit")
    public String tipoHabitacionEditarForm(@PathVariable("id") String id, Model model){
        Optional<TipoHabitacion> tipoHabitacionExistente= tipoHabitacionRepository.findById(id);
        if (tipoHabitacionExistente.isPresent()) {
            TipoHabitacion tipoHabitacion = tipoHabitacionExistente.get();
            model.addAttribute("tipoHabitacion", tipoHabitacion);
        }
        return "TipoHabitacionEditar";
        
    }

    @PostMapping("/tiposHab/{id}/edit/save")
    public String editar(@PathVariable("id") String id, @ModelAttribute TipoHabitacion tipoHabitacion){
        tipoHabitacionRepository.save(tipoHabitacion);
        return "redirect:/tiposHab";
    }

    @GetMapping("/tipoHabitaciones/{id}/delete")
    public String tipoHabitacionBorrar(@PathVariable("id") String id){
        Optional<TipoHabitacion> tipoHabitacionExistente= tipoHabitacionRepository.findById(id);
        if (tipoHabitacionExistente.isPresent()) {
            tipoHabitacionRepository.deleteById(id);
        }
        return "redirect:/tiposHab";
    }
}

