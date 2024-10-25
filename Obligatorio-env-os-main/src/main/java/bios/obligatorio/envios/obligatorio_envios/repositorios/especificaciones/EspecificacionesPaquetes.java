package bios.obligatorio.envios.obligatorio_envios.repositorios.especificaciones;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import bios.obligatorio.envios.obligatorio_envios.dominio.Paquete;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class EspecificacionesPaquetes {

    public static Specification<Paquete> nombreUsuarioContiene(String nombreUsuario) {
        if (nombreUsuario == null) return null;

        return new Specification<Paquete>() {
            @Override
            public Predicate toPredicate(Root<Paquete> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("nombreUsuario"), "%" + nombreUsuario + "%");
            }
        };
    }



    public static Specification<Paquete> buscar(String criterio, Pageable pageable) {
        if (criterio == null || criterio.isBlank()) return null;

        return Specification.where(nombreUsuarioContiene(criterio));
    }
}
