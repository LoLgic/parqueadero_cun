package com.example.parking.web.dto.ticket;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TicketCreateRequest(
		@NotNull(message = "El id del vehículo es obligatorio")
		@Positive(message = "El id del vehículo debe ser mayor a 0")
		Long idVehiculo,

		@NotNull(message = "El id del espacio es obligatorio")
		@Positive(message = "El id del espacio debe ser mayor a 0")
	    Long idEspacio
		) {
}
