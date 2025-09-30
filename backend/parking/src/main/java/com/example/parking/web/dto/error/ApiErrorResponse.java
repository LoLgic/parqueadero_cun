package com.example.parking.web.dto.error;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;

@Builder
public record ApiErrorResponse(
		int status,						// Código HTTP (ej: 400, 404, 500)
		String codigo,            		// Código interno del error (ej: E001)
	    String mensaje,         		// Mensaje estándar
	    @JsonInclude(JsonInclude.Include.NON_NULL)
	    List<String> detalles,    		// Lista de errores específicos (ej: validaciones)
	    LocalDateTime timestamp,  		// Fecha y hora
	    String path            			// Endpoint donde ocurrió el error
		) {
}
