package com.example.parking.domain.repo.tarifa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.parking.domain.model.tarifa.Tarifa;
import com.example.parking.domain.model.vehiculo.TipoVehiculo;

public interface TarifaRepository extends JpaRepository<Tarifa, Long> {
	Optional<Tarifa> findByTipoVehiculo(TipoVehiculo tipo);	
}
