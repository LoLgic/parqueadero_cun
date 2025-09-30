package com.example.parking.web.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest (
		@NotBlank(message = "El email es obligatorio")
        @Email(message = "El formato del email no es válido")
		@Size(max = 100, message = "El email no debe superar los 100 caracteres")
	    String email,
	    
	    @NotBlank(message = "La clave es obligatoria")
		@Size(min = 8, max = 100, message = "La clave debe tener entre 8 y 100 caracteres")
	    String clave) {
}
