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
import uniandes.edu.co.proyecto.Modelo.TipoHabitacionEmbedded;
import uniandes.edu.co.proyecto.Modelo.Habitacion;

import org.springframework.web.bind.annotation.RequestParam;

import uniandes.edu.co.proyecto.repositorio.HabitacionRepository;

@Controller
public class HabitacionController {
    @Autowired
    private HabitacionRepository habitacionRepository;

    @GetMapping("/habitaciones")
    public String habitaciones(Model model){
        model.addAttribute("habitaciones", habitacionRepository.findAll());
        return "Habitacion";
    }

    @GetMapping("/habitaciones/new")
    public String habitacionForm(Model model){
        model.addAttribute("habitacion", new Habitacion());
        return "HabitacionNuevo";       
    }

    @PostMapping("/habitaciones/new/save")
    public String habitacionGuardar(@ModelAttribute Habitacion habitacion){
        habitacionRepository.save(habitacion);
        return "redirect:/habitaciones";
    }

    @GetMapping("/habitaciones/{id}/edit")
    public String habitacionEditarForm(@PathVariable("id") String id, Model model){
        Optional<Habitacion> habitacionExistente= habitacionRepository.findById(id);
        if (habitacionExistente.isPresent()) {
            Habitacion habitacion = habitacionExistente.get();
            model.addAttribute("habitacion", habitacion);
        }
        return "HabitacionEditar";
        
    }

    @PostMapping("/habitaciones/{id}/edit/save")
    public String editar(@PathVariable("id") String id, @ModelAttribute Habitacion habitacion){
        habitacionRepository.save(habitacion);
        return "redirect:/habitaciones";
    }

    @GetMapping("/habitaciones/{id}/delete")
    public String habitacionBorrar(@PathVariable("id") String id){
        Optional<Habitacion> habitacionExistente= habitacionRepository.findById(id);
        if (habitacionExistente.isPresent()) {
            habitacionRepository.deleteById(id);
        }
        return "redirect:/habitaciones";
    }

    //Controladores para el manejo de tipohabitacion embebida

    @GetMapping("/addTipoHabitacion")
    public String anadirTipoHabitacion(@RequestParam(name = "id", required= false) String id, Model model){
        model.addAttribute("id", id);   
        model.addAttribute("tipoid", new TipoHabitacionEmbedded());
        return "addTipoHabitacionForm";
    }

    @PostMapping("/addTipoHabitacionSave")
    public String añadirTipoHabitacionSave(@RequestParam("id") String id,
    @ModelAttribute("tipoid") TipoHabitacionEmbedded tipoHabitacion){

        // Creamos un nuevo tipo de habitacion utilizando los datos del formulario
        TipoHabitacionEmbedded nuevoTipoHabitacion = new TipoHabitacionEmbedded(
            tipoHabitacion.getTipo(),
            tipoHabitacion.getDescripcion()
        );

        //Buscamos las habitaciones con ese id
        Optional<Habitacion> habitacionOpt = habitacionRepository.findById(id);

        //Añadimos ese tipoHabitacion a todas las Habitaciones con ese id
        if (habitacionOpt.isPresent()) {
            Habitacion habitacion = habitacionOpt.get();

            if (habitacion.getTipoHabitacion() == null) {
                List<TipoHabitacionEmbedded> emptyList = new ArrayList<>();
                habitacion.setTipoHabitacion(emptyList);
                habitacion.addTipoHabitacion(nuevoTipoHabitacion);
            }

            //Persistemos la modificacion en la base de datos
            habitacionRepository.save(habitacion);
        }
        
        return "redirect:/habitaciones";
    }
}

