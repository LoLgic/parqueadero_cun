package com.example.parking.web.dto.vehiculo;

import com.example.parking.domain.model.vehiculo.TipoVehiculo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record VehiculoRequest(
		@NotBlank(message = "La placa es obligatoria")
		//@Size(max = 6, message = "La placa debe tener 6 caractares, 3 letras seguidas de 3 numeros")
		String placa,
		
		@NotNull(message = "El tipo de vehículo es obligatorio")
		TipoVehiculo tipo
		) {

}
