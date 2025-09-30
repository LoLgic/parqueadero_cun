package com.example.parking.web.dto.usuario;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record UsuarioResponse(
		Long idUsuario, 
		String nombreCompleto, 
		String celular,
		String email,  
		String rol,
		Boolean activo,
		LocalDateTime fechaRegistro
		) {
}
