package com.example.parking.web.mapper.espacio;

import com.example.parking.domain.model.espacio.Espacio;
import com.example.parking.web.dto.espacio.EspacioRequest;
import com.example.parking.web.dto.espacio.EspacioResponse;

public class EspacioMapper {
	
	public static Espacio toEntity(EspacioRequest req) {
        return Espacio.builder()
                .codigo(req.codigo())
                .tipo(req.tipo())
                .ocupado(false) // por defecto libre
                .build();
    }

    public static EspacioResponse toResponse(Espacio e) {
        return EspacioResponse.builder()
                .idEspacio(e.getIdEspacio())
                .codigo(e.getCodigo())
                .tipo(e.getTipo().name())
                .ocupado(e.isOcupado())
                .build();
    }

}
