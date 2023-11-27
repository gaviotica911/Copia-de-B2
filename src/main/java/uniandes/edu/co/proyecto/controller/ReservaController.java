package uniandes.edu.co.proyecto.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import uniandes.edu.co.proyecto.Modelo.Reserva;

import uniandes.edu.co.proyecto.repositorio.ReservaRepository;


@Controller

public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;
    
    //@Autowired
    //private HabitacionRepository  habitacionRepository;

    @GetMapping("/reservas")
    public String reservas(Model model) {
        model.addAttribute("reservas", reservaRepository.findAll());
        return "reserva";
    }

    @GetMapping("/reservas/new")
    public String reservaForm(Model model){
        model.addAttribute("reserva", new Reserva());
       
        //model.addAttribute("habitaciones", habitacionRepository.darHabitaciones());

        return"reservaNueva";       
    }

    @PostMapping("/reservas/new/save")
    public String reservaGuardar(@ModelAttribute Reserva reserva){
      
        reservaRepository.save(reserva);
        return "redirect:/reservas";
    
    }

    @GetMapping("/reservas/{id}/edit")
    public String editarForm(@PathVariable String id, Model model){
      Optional<Reserva> reservaOpt = reservaRepository.findById(id);

        if (reservaOpt.isPresent()) {
            Reserva reserva = reservaOpt.get();
            model.addAttribute("reserva", reserva);
  
        } 
        return "ReservaEditar";
    }

    @PostMapping("/reservas/{id}/edit/save")
    public String editar(@PathVariable("id") String id, @ModelAttribute Reserva reserva){
        reservaRepository.save(reserva);
        return "redirect:/reservas";
    }


    

    @GetMapping("/reservas/{id}/delete")
    public String eliminarReserva(@PathVariable String id) {
    
        Optional<Reserva> reservaExistente = reservaRepository.findById(id);
       
        if (reservaExistente.isPresent()) {
            reservaRepository.deleteById(id);
        }
        return "redirect:/reservas";
    }



   




    
    
}
