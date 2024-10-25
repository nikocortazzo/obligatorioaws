package bios.obligatorio.envios.obligatorio_envios.servicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import bios.obligatorio.envios.obligatorio_envios.dominio.Paquete;
import bios.obligatorio.envios.obligatorio_envios.excepciones.ExcepcionProyectoEnvios;

public interface IServicioPaquete {
    
    Page<Paquete> buscar(String criterio, Pageable pageable);
    void agregar(Paquete paquete) throws ExcepcionProyectoEnvios;
    Paquete obtener(Integer id);
    void modificar(Paquete paquete) throws ExcepcionProyectoEnvios;
    void eliminar(Integer id) throws ExcepcionProyectoEnvios;
}

