package uniandes.edu.co.proyecto.controller;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;
import java.util.concurrent.TimeUnit;
import org.bson.Document;
import com.mongodb.client.AggregateIterable;

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


    @GetMapping("/usuarios/RFC3")
    public String RFC3(Model model, String id, String fecha1, String fecha2) {
        LookupOperation lookupOperation = LookupOperation.newLookup()
            .from("reservas")
            .localField("idReserva")
            .foreignField("_id")
            .as("reservas");

        Criteria idCriteria = Criteria.where("reservas.docUsuario").is(id);
        Criteria fechaCriteria = Criteria.where("fecha").gte(fecha1).lte(fecha2);
        Aggregation aggregation = Aggregation.newAggregation(
            lookupOperation,
            Aggregation.match(idCriteria.andOperator(fechaCriteria))
        );
        
        

       List<Reserva> reservas = mongoTemplate.aggregate(aggregation, "nombreDeTuColeccion", Reserva.class).getMappedResults();
        model.addAttribute("consumosXFecha", reservas);
        
        return "usuarioreq5";
    }

    @GetMapping("/reservas/req2")
    public String mostrarResultados(Model model) {
        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("habitacion")
                .localField("habitacionId")
                .foreignField("_id")
                .as("habitacion");

                Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.unwind("$habitacionId"),
                    lookupOperation,
                    Aggregation.lookup("consumos", "_id", "idReserva", "consumo"),
                    Aggregation.unwind("$consumo"),
                    Aggregation.match(Criteria.where("consumo.fecha")
                            .gte(new java.util.Date(1672531200000L))
                            .lt(new java.util.Date(1704067200000L))),
                    Aggregation.group("$habitacion._id").sum("precio").as("totalConsumos")
            );

        List<Reserva> results = mongoTemplate.aggregate(aggregation, "reservas", Reserva.class).getMappedResults();
        model.addAttribute("results", results);

        return "ReservaReq2";
    }

    
}
