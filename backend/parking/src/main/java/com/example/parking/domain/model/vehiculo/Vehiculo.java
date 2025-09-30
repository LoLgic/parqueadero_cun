package com.example.parking.domain.model.vehiculo;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;

import com.example.parking.domain.model.usuario.Usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vehiculos")
@Getter @Setter
@NoArgsConstructor 
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE vehiculos SET eliminado = true, "
		+ "fecha_eliminado = CURRENT_TIMESTAMP WHERE id_vehiculo = ?")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo")
    private Long idVehiculo;

    @Column(nullable = false, unique = true, length = 15)
    private String placa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoVehiculo tipo; // CARRO, MOTO, CAMIONETA

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    // Soft delete flags
    @Column(name = "eliminado", nullable = false)
    private boolean eliminado = false;

    @Column(name = "fecha_eliminado")
    private LocalDateTime fechaEliminado;

    @PreUpdate
    public void preUpdate() {
        if (this.eliminado && this.fechaEliminado == null) {
            this.fechaEliminado = LocalDateTime.now();
        }
    }

    // ==== MÉTODO DE AYUDA PARA "RESURRECCIÓN" ====
    public void restaurar() {
        this.eliminado = false;
        this.fechaEliminado = null;
        
        
    }
}

