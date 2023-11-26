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
    public String reservaEditarForm(@PathVariable("id") String id, @ModelAttribute Reserva reserva){
        Optional<Reserva> reservaExistente= reservaRepository.findById(id);
       if (reservaExistente.isPresent()) {
            //bebida.setId(id); // Asegura que el ID sea el mismo que el proporcionado en la URL
            reservaRepository.save(reserva);
             
        } 
        return "redirect:/reservas";
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable String id) {
        Optional<Reserva> reservaExistente = reservaRepository.findById(id);
        if (reservaExistente.isPresent()) {
            reservaRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

/*
    @GetMapping("/reservas/{id}/checkIn")
    public String checkIn(@PathVariable String id){
        reservaRepository.setEstado(id,true);
        return "redirect:/reservas";
    }


    @GetMapping("/reservas/{id}/checkOut")
    public String checkOut(@PathVariable String id){
        reservaRepository.setEstado(id,false);
        return "redirect:/reservas";
    }

 */

   




    
    
}
