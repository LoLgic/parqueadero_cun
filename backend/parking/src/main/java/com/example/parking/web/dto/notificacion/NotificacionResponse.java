package com.example.parking.web.dto.notificacion;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record NotificacionResponse(
		Long idNotificacion,
		//Usuario usuario,
		String mensaje,
		LocalDateTime fecha
		//String estado;  // LEIDA o NO_LEIDA
		) {

}
