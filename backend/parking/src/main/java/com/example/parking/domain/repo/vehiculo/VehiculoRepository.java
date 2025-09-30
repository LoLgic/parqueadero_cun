package com.example.parking.domain.repo.vehiculo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.parking.domain.model.usuario.Usuario;
import com.example.parking.domain.model.vehiculo.Vehiculo;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long>{

	List<Vehiculo> findByUsuario(Usuario usuario);
	
	// Busca SOLO vehículos no eliminados
    boolean existsByPlacaAndEliminadoFalse(String placa);
    
    // Busca vehículos incluso si están eliminados (para resurrección)
	Optional<Vehiculo> findByPlaca(String placa);
	
	// Busca por placa incluyendo también eliminados (ignora el @Where)
    @Query("SELECT v FROM Vehiculo v WHERE v.placa = :placa")
    Optional<Vehiculo> findByPlacaIncluirEliminados(@Param("placa") String placa);
}
