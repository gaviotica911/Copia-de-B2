package uniandes.edu.co.proyecto.controller;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;


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
import org.springframework.web.bind.annotation.RequestParam;

import uniandes.edu.co.proyecto.Modelo.Consumo;
import uniandes.edu.co.proyecto.Modelo.PlatosYBebidasEmbedded;
import uniandes.edu.co.proyecto.Modelo.ProductosEmbedded;
import uniandes.edu.co.proyecto.Modelo.Reserva;

import uniandes.edu.co.proyecto.Modelo.ServicioEmbedded;

import uniandes.edu.co.proyecto.repositorio.ConsumoRepository;


@Controller

public class ConsumoController {

    @Autowired
    private ConsumoRepository consumoRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
    
    //@Autowired
    //private HabitacionRepository  habitacionRepository;

    @GetMapping("/consumos")
    public String reservas(Model model) {
        model.addAttribute("consumos", consumoRepository.findAll());
        return "consumo";
    }

    @GetMapping("/consumos/new")
    public String consumoForm(Model model){
        model.addAttribute("consumo", new Consumo());
       
        //model.addAttribute("habitaciones", habitacionRepository.darHabitaciones());

        return "consumoNuevo";       
    }

    @PostMapping("/consumos/new/save")
    public String consumoGuardar(@ModelAttribute Consumo consumo){
      
        consumoRepository.save(consumo);
        return "redirect:/consumos";
    
    }

    @GetMapping("/consumos/{id}/edit")
    public String editarForm(@PathVariable String id, Model model){
      Optional<Consumo> consumoOpt = consumoRepository.findById(id);

        if (consumoOpt.isPresent()) {
            Consumo consumo = consumoOpt.get();
            model.addAttribute("consumo", consumo);
  
        } 
        return "ConsumoEditar";
    }

    @PostMapping("/consumos/{id}/edit/save")
    public String editar(@PathVariable("id") String id, @ModelAttribute Consumo consumo){
        consumoRepository.save(consumo);
        return "redirect:/consumos";
    }

    @GetMapping("/consumos/{id}/delete")
    public String eliminarConsumo(@PathVariable String id) {
    
        Optional<Consumo> consumoExistente = consumoRepository.findById(id);
       
        if (consumoExistente.isPresent()) {
            consumoRepository.deleteById(id);
        }
        return "redirect:/consumos";
    }

    //Controladores para el manejo de Procuctos, servicios y platosybebidas embebidos

    @GetMapping("/addProductoC")
    public String anadirProducto(@RequestParam(name = "nombre", required= false) String nombre, Model model){
        model.addAttribute("nombreTienda", nombre);   
        model.addAttribute("producto", new ProductosEmbedded());
        return "addProductoForm";
    }

    @PostMapping("/addProductoSaveC")
    public String anadirProductoSave(@RequestParam("id") String id,
    @ModelAttribute("producto") ProductosEmbedded product){

        // Creamos un nuevo producto utilizando los datos del formulario
        ProductosEmbedded nuevoProducto = new ProductosEmbedded(
            product.getNombre(),
            product.getDescripcion()
        );

        //Buscamos los consumos con ese id
        Optional<Consumo> consumoOpt = consumoRepository.findById(id);

        //Añadimos ese producto al consumo con ese id
        if (consumoOpt.isPresent()) {
            Consumo consumo = consumoOpt.get();

            if (consumo.getProductos() == null){
            List<ProductosEmbedded> emptyList = new ArrayList<>();
            consumo.setProductos(emptyList);
            }
            consumo.addProducto(nuevoProducto);

            //Persistemos la modificacion en la base de datos
            consumoRepository.save(consumo);
        }

        return "redirect:/consumos";

    }
    
    @GetMapping("/addPlatoYBebidaC")
    public String anadirBebida(@RequestParam(name = "nombre", required = false) String nombre, Model model){
        model.addAttribute("nombreTipoPlatoYBebida", nombre);
        model.addAttribute("platoybebida", new PlatosYBebidasEmbedded());


        return "addPlatoYBebidaForm";
    }

    @PostMapping("/addPlatoYBebidaSaveC")
    public String anadirBebidaSave(@RequestParam("nombreTipoPlatoYBebida") String nombreTipoPlatoYBebida,
    @ModelAttribute("platoybebida") PlatosYBebidasEmbedded platoybebida){

        // Creamos una nueva bebida utilizando los datos del formulario
        PlatosYBebidasEmbedded nuevoPlatoYBebida = new PlatosYBebidasEmbedded(
            platoybebida.getNombre(),
            platoybebida.getDescripcion()
        );

        //Buscamos los consumos con ese id
        Optional<Consumo> consumoOpt = consumoRepository.findById(nombreTipoPlatoYBebida);

        //Añadimos ese platoybebida al consumo con ese id
        if (consumoOpt.isPresent()) {
            Consumo consumo = consumoOpt.get();

            if (consumo.getProductos() == null){
            List<PlatosYBebidasEmbedded> emptyList = new ArrayList<>();
            consumo.setPlatosybebidas(emptyList);
            }
            consumo.addPlatoYBebida(nuevoPlatoYBebida);

            //Persistemos la modificacion en la base de datos
            consumoRepository.save(consumo);
        }

        return "redirect:/consumos";

    }

    @GetMapping("/addServicio")
    public String anadirServicio(@RequestParam(name = "nombre", required = false) String nombre, Model model){
        model.addAttribute("id", nombre);
        model.addAttribute("servicio", new ServicioEmbedded());

        return "addServicioForm";
    }

    @PostMapping("/addServicioSave")
    public String añadirBebidaSave(@RequestParam("nombre") String nombreTipoPlatoYBebida,
    @ModelAttribute("platoybebida") ServicioEmbedded servicio){

        // Creamos una nueva bebida utilizando los datos del formulario
        ServicioEmbedded nuevoServicio = new ServicioEmbedded(
            servicio.getDescripcion(),
            servicio.getTipo(),
            servicio.getDuracion()
        );

        //Buscamos los consumos con ese id
        Optional<Consumo> consumoOpt = consumoRepository.findById(nombreTipoPlatoYBebida);

        //Añadimos ese servicio al consumo con ese id
        if (consumoOpt.isPresent()) {
            Consumo consumo = consumoOpt.get();

            if (consumo.getServicios() == null){
            List<ServicioEmbedded> emptyList = new ArrayList<>();
            consumo.setServicios(emptyList);
            }
            consumo.addServicio(nuevoServicio);

            //Persistemos la modificacion en la base de datos
            consumoRepository.save(consumo);
        }

        return "redirect:/consumos";

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
}

