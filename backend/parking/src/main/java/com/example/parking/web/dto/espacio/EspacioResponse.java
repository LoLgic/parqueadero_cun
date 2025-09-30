package com.example.parking.web.dto.espacio;

import lombok.Builder;

@Builder
public record EspacioResponse(
		Long idEspacio, 
		String codigo, 
		String tipo, 
		boolean ocupado
		) {
}
