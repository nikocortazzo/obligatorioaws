package bios.obligatorio.envios.obligatorio_envios.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import bios.obligatorio.envios.obligatorio_envios.dominio.Paquete;
import bios.obligatorio.envios.obligatorio_envios.excepciones.ExcepcionNoExiste;
import bios.obligatorio.envios.obligatorio_envios.excepciones.ExcepcionProyectoEnvios;
import bios.obligatorio.envios.obligatorio_envios.excepciones.ExcepcionYaExiste;
import bios.obligatorio.envios.obligatorio_envios.repositorios.IRepositorioPaquetes;
import bios.obligatorio.envios.obligatorio_envios.repositorios.especificaciones.EspecificacionesPaquetes;

@Service
public class ServicioPaquete implements IServicioPaquete{

    @Autowired
    private IRepositorioPaquetes repositorioPaquetes;

    @Override
    public Page<Paquete> buscar(String criterio, Pageable pageable) {
        return repositorioPaquetes.findAll(EspecificacionesPaquetes.buscar(criterio, pageable),pageable);
    }


    @Override
    public void agregar(Paquete paquete) throws ExcepcionProyectoEnvios {
        Paquete paqueteExistente = repositorioPaquetes.findById(paquete.getId()).orElse(null);

        if(paqueteExistente != null) {
            throw new ExcepcionYaExiste("El estado ya existe.");
        }

        repositorioPaquetes.save(paquete);
    }

    @Override
    public Paquete obtener(Integer id) {
        return repositorioPaquetes.findById(id).orElse(null);
    }

    @Override
    public void modificar(Paquete paquete) throws ExcepcionProyectoEnvios {
        Paquete paqueteExiste = repositorioPaquetes.findById(paquete.getId()).orElse(null);

        if (paqueteExiste == null) throw new ExcepcionNoExiste("El paquete no existe");

        repositorioPaquetes.save(paquete);
    }

    @Override
    public void eliminar(Integer id) throws ExcepcionProyectoEnvios {
        Paquete paqueteExistente = repositorioPaquetes.findById(id).orElse(null);

        if(paqueteExistente == null) {
            throw new ExcepcionNoExiste("El paquete no existe.");
        }

        repositorioPaquetes.deleteById(id);
    }
    
}
