package bios.obligatorio.envios.obligatorio_envios.controladores;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bios.obligatorio.envios.obligatorio_envios.dominio.Cliente;
import bios.obligatorio.envios.obligatorio_envios.excepciones.ExcepcionProyectoEnvios;
import bios.obligatorio.envios.obligatorio_envios.servicios.IServicioClientes;
import jakarta.validation.Valid;

@Controller
public class ControladorClientes {
    
    @Autowired
    IServicioClientes servicioClientes;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/registrarcliente")
    public String registro(@ModelAttribute Cliente cliente) {
        return "registro";
    }

    @PostMapping("/registrarcliente")
    public String procesarRegistro(@ModelAttribute @Valid Cliente cliente, BindingResult result,Model model, RedirectAttributes attributes, @RequestParam(value = "passwordrepetida") String passwordrepetida) {

        if (result.hasErrors() || !cliente.getClave().equals(passwordrepetida)) {            
            if (!cliente.getClave().equals(passwordrepetida)) {                
                result.rejectValue("clave", "error.clave", "Las contraseñas no coinciden.");
            }            
            return "registro";
        }
        try{            
            servicioClientes.registrar(cliente);
            attributes.addFlashAttribute("mensaje","Se creó la cuenta con éxito.");
            return "redirect:/";
        }
        catch(ExcepcionProyectoEnvios e) {
            model.addAttribute("mensaje", "Error "+ e.getMessage());
            return "registro";
        }        
    }

    @GetMapping("/micuenta")
    public String miCuenta(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
        Cliente cliente = servicioClientes.obtener(nombreUsuario);

        if (cliente != null)
            model.addAttribute("cliente",cliente);
        else
            model.addAttribute("mensaje", "Error. No se pudieron obtener los datos de la cuenta");

        return "clientes/mi-cuenta";
    }

    @GetMapping("/micuenta/editar")
    public String editarCuenta(@ModelAttribute Cliente cliente, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
        cliente = servicioClientes.obtener(nombreUsuario);

        if (cliente != null)
            model.addAttribute("cliente",cliente);
        else
            model.addAttribute("mensaje", "Error. No se pudieron obtener los datos de la cuenta");


        return "clientes/editar-cuenta";
    }

    @PostMapping("/micuenta/editar")
    public String procesarEditarCuenta(@ModelAttribute Cliente cliente, BindingResult result, Model model, RedirectAttributes attributes, @RequestParam(required = false) String passwordrepetida, 
    @RequestParam(name = "grupo1") String cambiarContrasena) {

        System.out.println(cliente);

        if (result.hasErrors()) {
            model.addAttribute("mensaje", "Error al editar los datos de la cuenta.");
            return "clientes/editar-cuenta";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
        cliente.setNombreUsuario(nombreUsuario);

        try {            
            //Validacion manual al no usar @Valid, permite no cambiar la contraseña ya que no se puede desencriptar de la db y llevarla a la vista
            if (cliente.getNombreUsuario().isEmpty() || cliente.getNombreUsuario() == null) {
                model.addAttribute("mensaje", messageSource.getMessage("NotBlank.cliente.nombreUsuario", null, Locale.getDefault()));
                return "clientes/editar-cuenta";
            }

            if (cliente.getCedula().isEmpty() || cliente.getCedula() == null) {
                model.addAttribute("mensaje", messageSource.getMessage("NotBlank.cliente.cedula", null, Locale.getDefault()));
                return "clientes/editar-cuenta";
            }

            if (cliente.getDomicilio().isEmpty() || cliente.getDomicilio() == null) {
                model.addAttribute("mensaje", messageSource.getMessage("NotBlank.cliente.domicilio", null, Locale.getDefault()));
                return "clientes/editar-cuenta";
            }
            if (cliente.getTelefono().isEmpty() || cliente.getTelefono() == null) {
                model.addAttribute("mensaje", messageSource.getMessage("NotBlank.cliente.telefono", null, Locale.getDefault()));
                return "clientes/editar-cuenta";
            }


            if ("Si".equals(cambiarContrasena)) {                

                if (cliente.getClave().isEmpty() || cliente.getClave() == null) {
                    model.addAttribute("mensaje", messageSource.getMessage("NotBlank.cliente.clave", null, Locale.getDefault()));
                    return "clientes/editar-cuenta";
                }

                if (!passwordrepetida.equals(cliente.getClave())) {
                    model.addAttribute("mensaje", "Error. Las contraseñas no coinciden.");
                    return "clientes/editar-cuenta";
                }

                servicioClientes.modificar(cliente, true);
            }
            else {
                servicioClientes.modificar(cliente, false);
            }

            attributes.addFlashAttribute("mensaje", "Se han modificado los datos de la cuenta con éxito");
            return "redirect:/micuenta";
        } catch (ExcepcionProyectoEnvios e) {
            model.addAttribute("mensaje", "Error al modificar la cuenta. " + e.getMessage());
            return "clientes/editar-cuenta";
        }    
    }

    @GetMapping("/micuenta/eliminar")
    public String eliminarCuenta() {

        return "clientes/eliminar-cuenta";
    }

    @PostMapping("/micuenta/eliminar")
    public String procesarEliminarCuenta() {

        return "clientes/eliminar-cuenta";
    }

}
