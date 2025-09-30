package com.example.parking.domain.repo.pago;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.parking.domain.model.pago.Pago;
import com.example.parking.domain.model.usuario.Usuario;

public interface PagoRepository extends JpaRepository<Pago, Long> {

	// Buscar todos los pagos de un usuario (relación via ticket)
    List<Pago> findByTicket_Usuario(Usuario usuario);

}
