package com.example.parking.web.dto.ticket;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record TicketResponse(
		Long idTicket,
		String nombre,
		String emailUsuario,
		String tipo,
		String placaVehiculo,
		String codigoEspacio,
		LocalDateTime fechaEntrada,
		LocalDateTime fechaSalida,
		String estado,
		BigDecimal montoPago
		) {

}
