package bios.obligatorio.envios.obligatorio_envios.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import bios.obligatorio.envios.obligatorio_envios.dominio.Paquete;
import bios.obligatorio.envios.obligatorio_envios.excepciones.ExcepcionProyectoEnvios;
import bios.obligatorio.envios.obligatorio_envios.servicios.IServicioEstadoRastreo;
import bios.obligatorio.envios.obligatorio_envios.servicios.IServicioPaquete;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/paquetes")
public class ControladorPaquetes {

    @Autowired
    private IServicioPaquete servicioPaquete;

    @Autowired
    private IServicioEstadoRastreo servicioEstadoRastreo;

    //@Autowired
    //private IServicioCategoria servicioCategoria;
    
    @GetMapping
    public String paquetes(@RequestParam(required = false) String criterio, Model model, Pageable pageable) {
        Page<Paquete> paquetes = servicioPaquete.buscar(criterio, pageable);
        model.addAttribute("paquetes", paquetes);

        return "paquetes/index";
    }

    @GetMapping("/{id}")
    public String verDetallePaquete(@PathVariable Integer id, Model model) {
        Paquete paquetes = servicioPaquete.obtener(id);
        if (paquetes != null)
            model.addAttribute("paquetes", paquetes);
        else
            model.addAttribute("mensaje", "Error. No se encontró el paquete " + id + ".");
        
        return "paquetes/detalle";
    }

    @GetMapping("/listar")
    public String verEnvios(String cedula, Model model) {
        
        return "paquetes/listar";
    }

    @GetMapping("/agregar")
    public String mostrarAgregarPaquete(@ModelAttribute Paquete paquete, Model model) {
        model.addAttribute("estados", servicioEstadoRastreo.listar());
        //model.addAttribute("categorias", servicioCategorias.listar());

        return "paquetes/agregar";
    }

    @PostMapping("/agregar")
    public String procesarAgregar(@ModelAttribute @Valid Paquete paquete, BindingResult result, Model model, RedirectAttributes attributes) {
        model.addAttribute("estados", servicioEstadoRastreo.listar());
        //model.addAttribute("categorias", servicioCategorias.listar());

        if (result.hasErrors()) {
            return "paquetes/agregar";
        }

        try {
            servicioPaquete.agregar(paquete);

            attributes.addFlashAttribute("mensaje", "Paquete agregado con éxito.");

            return "redirect:/paquetes";
        } catch (ExcepcionProyectoEnvios e) {
            model.addAttribute("mensaje", "¡ERROR! " + e.getMessage());

            return "paquetes/agregar";
        }
    }

    @GetMapping("/modificar")
    public String modificarPaquete(Integer id, Model model) {
        model.addAttribute("estados", servicioEstadoRastreo.listar());
        //model.addAttribute("categorias", servicioCategoria.listar());

        Paquete paquetes = servicioPaquete.obtener(id);        

        if (paquetes != null) {
            model.addAttribute("paquetes",paquetes);
        } else {
            model.addAttribute("mensaje","Error. No se encontró el paquete "+ id +".");
        }        

        return "paquetes/modificar";
    }

    @PostMapping("/modificar")
    public String procesarModificarPaquete(@ModelAttribute @Valid Paquete paquete, BindingResult result, RedirectAttributes attributes, Model model) {

        if (result.hasErrors())
            return "paquetes/modificar";

        try {
            servicioPaquete.modificar(paquete);
            attributes.addFlashAttribute("mensaje","Se modificó el paquete con éxito");

            return "redirect:/paquetes";
        } catch (ExcepcionProyectoEnvios e) {
            model.addAttribute("mensaje", "Error al modificar el paquete " + e.getMessage());
            return "paquetes/modificar";
        }        
    }

    @GetMapping("/eliminar")
    public String mostrarEliminarPaquete(Integer id, Model model) {
        Paquete paquete = servicioPaquete.obtener(id);

        if (paquete != null) {
            model.addAttribute("producto", paquete);
        } else {
            model.addAttribute("mensaje", "¡ERROR! No se encontró el producto con el código " + id + ".");
        }

        return "paquetes/eliminar";
    }

    @PostMapping("/eliminar")
    public String procesarEliminarPaquete(Integer id, Model model, RedirectAttributes attributes) {
        try {
            servicioPaquete.eliminar(id);

            attributes.addFlashAttribute("mensaje", "Paquete eliminado con éxito.");

            return "redirect:/paquetes";
        } catch (ExcepcionProyectoEnvios e) {
            model.addAttribute("mensaje", "¡ERROR! " + e.getMessage());

            return "paquetes/eliminar";
        }
    }
}
