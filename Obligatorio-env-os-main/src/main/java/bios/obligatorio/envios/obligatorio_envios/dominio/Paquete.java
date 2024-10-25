package bios.obligatorio.envios.obligatorio_envios.dominio;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "paquetes")
public class Paquete {
    
    @Id
    @NotNull
    @Min(0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull
    @PastOrPresent
    LocalDateTime fechaHora;

    @NotBlank
    @Column(length = 20, nullable = false)
    String nombreDestinatario;

    @NotBlank
    @Column(length = 60, nullable = false)
    String direccionDestinatario;

    @NotBlank
    @Column(length = 20, nullable = false)
    String telefonoDestinatario;

    @NotNull
    Boolean cobroADestinatario;

    @ManyToOne
    @NotNull
    Categoria categoria;

    @ManyToOne
    @NotNull
    EstadoRastreo estadoRastreo;

    @NotNull
    @ManyToOne
    Cliente cliente;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getNombreDestinatario() {
        return nombreDestinatario;
    }

    public void setNombreDestinatario(String nombreDestinatario) {
        this.nombreDestinatario = nombreDestinatario;
    }

    public String getDireccionDestinatario() {
        return direccionDestinatario;
    }

    public void setDireccionDestinatario(String direccionDestinatario) {
        this.direccionDestinatario = direccionDestinatario;
    }

    public String getTelefonoDestinatario() {
        return telefonoDestinatario;
    }

    public void setTelefonoDestinatario(String telefonoDestinatario) {
        this.telefonoDestinatario = telefonoDestinatario;
    }

    public Boolean getCobroADestinatario() {
        return cobroADestinatario;
    }

    public void setCobroADestinatario(Boolean cobroADestinatario) {
        this.cobroADestinatario = cobroADestinatario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public EstadoRastreo getEstadoRastreo() {
        return estadoRastreo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setEstadoRastreo(EstadoRastreo estadoRastreo) {
        this.estadoRastreo = estadoRastreo;
    }

    public Paquete() {
    }

}
