package com.example.parking.domain.repo.espacio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.parking.domain.model.espacio.Espacio;
import com.example.parking.domain.model.vehiculo.TipoVehiculo;

public interface EspacioRepository extends JpaRepository<Espacio, Long>{
	List<Espacio> findByTipoAndOcupadoFalse(TipoVehiculo tipo);

	boolean existsByCodigo(String codigo);
}
