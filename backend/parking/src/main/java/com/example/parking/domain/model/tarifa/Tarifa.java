package com.example.parking.domain.model.tarifa;

import java.math.BigDecimal;

import com.example.parking.domain.model.vehiculo.TipoVehiculo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tarifas")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class Tarifa {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tarifa")
    private Long idTarifa;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo_vehiculo", nullable = false, unique = true)
    private TipoVehiculo tipoVehiculo;

    @Column(name="valor_hora",  nullable = false, precision = 10, scale = 2)
    private BigDecimal valorHora;
}
