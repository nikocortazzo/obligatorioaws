package bios.obligatorio.envios.obligatorio_envios.dominio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "estados_rastreo")
public class EstadoRastreo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotBlank
    @Column(length = 100, nullable = false)
    String descripcion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoRastreo() {
        this(null, null);
    }

    public EstadoRastreo(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }
}
