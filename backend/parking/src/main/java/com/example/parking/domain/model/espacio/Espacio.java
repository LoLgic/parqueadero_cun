package com.example.parking.domain.model.espacio;

import java.time.LocalDateTime;

import com.example.parking.domain.model.vehiculo.TipoVehiculo;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "espacios")
@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class Espacio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_espacio")
    private Long idEspacio;

    @Column(nullable = false, unique = true, length = 10)
    private String codigo;  // Ej: "A1", "B2"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoVehiculo tipo; // CARRO, MOTO, CAMIONETA

    @Column(nullable = false)
    private boolean ocupado = false;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    public void prePersist() {
        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDateTime.now();
        }
    }
}

