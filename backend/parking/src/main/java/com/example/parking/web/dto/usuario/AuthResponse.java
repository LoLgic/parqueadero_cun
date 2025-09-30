package com.example.parking.web.dto.usuario;

public record AuthResponse(
		String token, 
		String rol, 
		Long idUsuario, 
		String nombre) {
}
