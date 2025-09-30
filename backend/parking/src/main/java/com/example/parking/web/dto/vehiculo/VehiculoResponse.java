package com.example.parking.web.dto.vehiculo;

import lombok.Builder;

@Builder
public record VehiculoResponse(
		Long idVehiculo,
	    String placa,
	    String tipo,
	    String propietario
		) {

}
