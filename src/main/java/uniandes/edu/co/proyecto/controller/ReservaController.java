package uniandes.edu.co.proyecto.controller;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import uniandes.edu.co.proyecto.Modelo.Reserva;
import uniandes.edu.co.proyecto.Modelo.ResultadoReq2;
import uniandes.edu.co.proyecto.repositorio.ReservaRepository;


@Controller

public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
private MongoTemplate mongoTemplate;
    
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


   

    @GetMapping("/reservas/req2")
    public String mostrarResultados(Model model) {
        // Definir las operaciones de agregación
        LookupOperation lookupHabitacion = LookupOperation.newLookup()
                .from("habitacion")
                .localField("habitacionId")
                .foreignField("_id")
                .as("habitacion");

        LookupOperation lookupConsumos = LookupOperation.newLookup()
                .from("consumos")
                .localField("_id")
                .foreignField("idReserva")
                .as("consumo");

        // Filtro para la fecha
        Criteria fechaCriteria = Criteria.where("consumo.fecha")
                .gte(new java.util.Date(1672531200000L))
                .lt(new java.util.Date(1704067200000L));

        // Agregación
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("$habitacionId"),
                lookupHabitacion,
                lookupConsumos,
                Aggregation.unwind("$consumo"),
                Aggregation.match(fechaCriteria),
                Aggregation.group("$habitacionId")
                        .sum("consumo.precio").as("totalConsumos"),
                Aggregation.project()
                        .and("_id").as("habitacionId")
                        .and("totalConsumos").as("totalConsumos")
        );

        // Ejecutar la consulta de agregación
        List<ResultadoReq2> resultados = mongoTemplate.aggregate(aggregation, "reservas", ResultadoReq2.class).getMappedResults();

        model.addAttribute("resultados", resultados);

        return "ReservaReq2";
    }

}
