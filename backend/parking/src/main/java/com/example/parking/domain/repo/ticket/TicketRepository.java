package com.example.parking.domain.repo.ticket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.parking.domain.model.ticket.EstadoTicket;
import com.example.parking.domain.model.ticket.Ticket;
import com.example.parking.domain.model.vehiculo.Vehiculo;

public interface TicketRepository extends JpaRepository<Ticket, Long>{

	List<Ticket> findByUsuario_IdUsuario(Long idUsuario);

    boolean existsByEspacio_IdEspacioAndEstadoIn(Long idEspacio, List<EstadoTicket> estados);

	boolean existsByVehiculoAndEstado(Vehiculo vehiculo, EstadoTicket finalizado);
}
