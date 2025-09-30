package com.example.parking.web.dto.ticket;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.parking.web.dto.espacio.EspacioResponse;
import com.example.parking.web.dto.usuario.UsuarioResponse;
import com.example.parking.web.dto.vehiculo.VehiculoResponse;

import lombok.Builder;

@Builder
public record AdminTicketResponse(
		Long idTicket,
		UsuarioResponse usuario,
		VehiculoResponse vehiculo,
		EspacioResponse espacio,
		LocalDateTime fechaEntrada,
		LocalDateTime fechaSalida,
		String estado,
		BigDecimal montoPago
		) {
}
