package bios.obligatorio.envios.obligatorio_envios.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import bios.obligatorio.envios.obligatorio_envios.dominio.Categoria;

public interface IRepositorioCategorias extends JpaRepository<Categoria,Integer> {
    
}
