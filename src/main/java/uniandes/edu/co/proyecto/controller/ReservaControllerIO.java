package uniandes.edu.co.proyecto.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import uniandes.edu.co.proyecto.Modelo.Reserva;
import uniandes.edu.co.proyecto.repositorio.ReservaRepository;


@Controller

public class ReservaControllerIO {

    @Autowired
    private ReservaRepository reservaRepository;
 

    @GetMapping("/reservasIO")
    public String reservas(Model model) {
        model.addAttribute("reservas", reservaRepository.findAll());
        return "reservaIO";
    }


    @GetMapping("/reservasIO/{id}/checkIn")
   public String checkIn(@PathVariable String id) {
        Optional<Reserva> reservaOpt = reservaRepository.findById(id);

        if (reservaOpt.isPresent()) {
            Reserva reserva = reservaOpt.get();
            reserva.setEstado(true);
            reservaRepository.save(reserva);
        }
        return "redirect:/reservasIO";
    }

    @GetMapping("/reservasIO/{id}/checkOut")
    public String checkOut(@PathVariable String id){
      Optional<Reserva> reservaOpt = reservaRepository.findById(id);

        if (reservaOpt.isPresent()) {
            Reserva reserva = reservaOpt.get();
            reserva.setEstado(false);
            reservaRepository.save(reserva);
            
        } 
        return "redirect:/reservasIO";
    }
   




    
    
}