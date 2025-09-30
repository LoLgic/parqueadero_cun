package com.example.parking.web.dto.usuario;

import jakarta.validation.constraints.*;

public record RegistroClienteRequest (
		
		@NotBlank(message = "El nombre es obligatorio")
        @Size(max = 50, message = "El nombre no debe superar los 50 caracteres")
		String nombre,
		@NotBlank(message = "El apellido es obligatorio")
        @Size(max = 50, message = "El apellido no debe superar los 50 caracteres")
		String apellido,
		
		@NotBlank(message = "El número de CI es obligatorio")
		@Size(max = 20, message = "El CI no debe superar los 20 caracteres")
		String celular,
		
		@NotBlank(message = "El email es obligatorio")
        @Email(message = "El formato del email no es válido")
        @Size(max = 100, message = "El email no debe superar los 100 caracteres")
		String email,
		
		@NotBlank(message = "La clave es obligatoria")
        @Size(min = 8, max = 100, message = "La clave debe tener entre 8 y 100 caracteres")
		String clave
		) {
}
