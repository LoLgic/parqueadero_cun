package com.example.parking.web.mapper.vehiculo;

import com.example.parking.domain.model.usuario.Usuario;
import com.example.parking.domain.model.vehiculo.Vehiculo;
import com.example.parking.web.dto.vehiculo.VehiculoRequest;
import com.example.parking.web.dto.vehiculo.VehiculoResponse;

public class VehiculoMapper {

	public static Vehiculo toEntity(VehiculoRequest req, Usuario usuario) {
        return Vehiculo.builder()
                .placa(req.placa())
                .tipo(req.tipo())
                .usuario(usuario)
                .build();
    }

    public static VehiculoResponse toResponse(Vehiculo v) {
        return VehiculoResponse.builder()
                .idVehiculo(v.getIdVehiculo())
                .placa(v.getPlaca())
                .tipo(v.getTipo().name())
                .propietario(v.getUsuario().getNombre() + " " + v.getUsuario().getApellido())
                .build();
    }
}
