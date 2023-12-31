package uniandes.edu.co.proyecto.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators.Cond;
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
import uniandes.edu.co.proyecto.Modelo.ResultadoReq1;
import uniandes.edu.co.proyecto.Modelo.ResultadoReqAvanzado;
import uniandes.edu.co.proyecto.repositorio.HabitacionRepository;
import uniandes.edu.co.proyecto.repositorio.ReservaRepository;


@Controller

public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;
    
    @Autowired
    private HabitacionRepository  habitacionRepository;

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

    @GetMapping("/reservas/req1")
    public String mostrarResultados2(Model model) {
        // Definir las operaciones de agregación
        
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        Date oneYearAgoDate = Date.from(oneYearAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Build the aggregation pipeline
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(
                Criteria.where("fechaEntrada").gte(oneYearAgoDate).orOperator(
                    Criteria.where("fechaSalida").gte(oneYearAgoDate)
                )
            ),
            Aggregation.unwind("habitacionId"),
            Aggregation.project()
                .and("habitacionId").as("habitacionId")
                .andExpression("(fechaSalida - fechaEntrada) / 86400000").as("duracionEstadia"), // 86400000 = milliseconds in a day
            Aggregation.group("habitacionId")
                .sum("duracionEstadia").as("totalDiasOcupados"),
            Aggregation.project()
                .and("_id").as("habitacionId")
                .andExpression("(totalDiasOcupados / 365) * 100").as("porcentajeOcupacion")
        );


        // Ejecutar la consulta de agregación
        List<ResultadoReq1> resultados = mongoTemplate.aggregate(aggregation, "reservas", ResultadoReq1.class).getMappedResults();

        model.addAttribute("resultados", resultados);

        return "ReservaReq1";
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

    @GetMapping("/reservas/reqAvanzado")
    public String mostrarReqAvanzado(Model model) {

        // Fechas de inicio y fin para la comparación
        Date startDate = new Date(1672531200000L);  // Ajusta estas fechas según tu lógica
        Date endDate = new Date(1704067200000L);

        // Expresiones condicionales para los trimestres
        AggregationExpression firstTrimesterExpression = Cond.newBuilder()
                .when(Criteria.where("fechaEntrada").gte(startDate).lt(new Date(1680307200000L)))
                .then(1)
                .otherwise(0);

        AggregationExpression secondTrimesterExpression = Cond.newBuilder()
                .when(Criteria.where("fechaEntrada").gte(new Date(1680307200000L)).lt(new Date(1688169600000L)))
                .then(2)
                .otherwise(0);

        AggregationExpression thirdTrimesterExpression = Cond.newBuilder()
                .when(Criteria.where("fechaEntrada").gte(new Date(1688169600000L)).lt(new Date(1696118400000L)))
                .then(3)
                .otherwise(0);

        // Operación de agregación
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("fechaEntrada").gte(startDate).lt(endDate)),
                Aggregation.group("docUsuario")
                        .addToSet(firstTrimesterExpression).as("firstTrimester")
                        .addToSet(secondTrimesterExpression).as("secondTrimester")
                        .addToSet(thirdTrimesterExpression).as("thirdTrimester")
                        .addToSet("fechaEntrada").as("fechas"),
                Aggregation.match(Criteria.where("firstTrimester").is(1).and("secondTrimester").is(2).and("thirdTrimester").is(3)),
                Aggregation.project().andExclude("_id")
                        .and("docUsuario").as("docUsuario")
                        .andInclude("fechas")
        );

        // Ejecutar la consulta de agregación
        List<ResultadoReqAvanzado> resultados = mongoTemplate.aggregate(aggregation, "reservas", ResultadoReqAvanzado.class).getMappedResults();
        model.addAttribute("resultados", resultados);

        return "ReservaReqAvanzado";
    }

}
