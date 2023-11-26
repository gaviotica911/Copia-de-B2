package uniandes.edu.co.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class B2Controller {
    @RequestMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/IndexCliente")
    public String iniciarCliente() {
        // Lógica para el inicio de sesión del cliente
        return "IndexCliente"; // Esto redirige a la página del cliente
    }

    @GetMapping("/indexAdminSistema")
    public String iniciarAdmin() {
        // Lógica para el inicio de sesión del administrador del sistema
        return "indexAdminSistema"; // Esto redirige a la página del administrador
    }

    @GetMapping("/IndexEmpleado")
    public String iniciarEmpleado() {
        // Lógica para el inicio de sesión del empleado o recepcionista
        return "IndexEmpleado"; // Esto redirige a la página del empleado o recepcionista
    }
    @GetMapping("/indexRecepcionista")
    public String iniciarRecepcionista() {
        // Lógica para el inicio de sesión del empleado o recepcionista
        return "indexRecepcionista"; // Esto redirige a la página del empleado o recepcionista
    }

    @GetMapping("/indexGerente")
    public String iniciarGerente() {
        // Lógica para el inicio de sesión del empleado o recepcionista
        return "indexGerente"; // Esto redirige a la página del empleado o recepcionista
    }
    

    
}
