package com.example.parking.domain.model.ticket;

import java.time.LocalDateTime;

import com.example.parking.domain.model.espacio.Espacio;
import com.example.parking.domain.model.usuario.Usuario;
import com.example.parking.domain.model.vehiculo.Vehiculo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket")
    private Long idTicket;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario; // quién creó la reserva

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculo vehiculo; // vehículo que ocupa el espacio

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_espacio", nullable = false)
    private Espacio espacio; // espacio asignado
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoTicket estado; // ACTIVO, FINALIZADO

    @Column(name = "fecha_entrada", nullable = false, updatable = false)
    private LocalDateTime fechaEntrada;

    @Column(name = "fecha_salida")
    private LocalDateTime fechaSalida;

    // ===== HOOK para inicializar fechaEntrada =====
    @PrePersist
    public void prePersist() {
        if (this.fechaEntrada == null) {
            this.fechaEntrada = LocalDateTime.now();
        }
    }
}

