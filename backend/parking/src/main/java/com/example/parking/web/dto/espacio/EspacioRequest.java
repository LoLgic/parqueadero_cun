package com.example.parking.web.dto.espacio;

import com.example.parking.domain.model.vehiculo.TipoVehiculo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EspacioRequest (
		@NotBlank(message = "El código del espacio es obligatorio")
        @Size(max = 10, message = "El código no puede tener más de 10 caracteres")
		String codigo,
		
		@NotNull(message = "El tipo de vehículo es obligatorio")
	    TipoVehiculo tipo	
		){
}
