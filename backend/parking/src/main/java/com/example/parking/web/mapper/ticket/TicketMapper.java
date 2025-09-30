package com.example.parking.web.mapper.ticket;

import java.math.BigDecimal;
import java.util.Optional;

import com.example.parking.domain.model.ticket.Ticket;
import com.example.parking.web.dto.espacio.EspacioResponse;
import com.example.parking.web.dto.ticket.AdminTicketResponse;
import com.example.parking.web.dto.ticket.TicketResponse;
import com.example.parking.web.dto.usuario.UsuarioResponse;
import com.example.parking.web.dto.vehiculo.VehiculoResponse;

public class TicketMapper {

	public static TicketResponse toResponse(Ticket t, Optional<BigDecimal> montoOpt) {
        return TicketResponse.builder()
                .idTicket(t.getIdTicket())
                .nombre(t.getUsuario().getNombre() + " " + t.getUsuario().getApellido())
                .emailUsuario(t.getUsuario().getEmail())
                .tipo(t.getVehiculo().getTipo().name())
                .placaVehiculo(t.getVehiculo().getPlaca())
                .codigoEspacio(t.getEspacio().getCodigo())
                .fechaEntrada(t.getFechaEntrada())
                .fechaSalida(t.getFechaSalida())
                .estado(t.getEstado().name())
                .montoPago(montoOpt.orElse(null))
                .build();
    }
	
	public static AdminTicketResponse toResponseAdmin(Ticket t, Optional<BigDecimal> montoOpt) {
        UsuarioResponse usuarioResponse = UsuarioResponse.builder()
                .idUsuario(t.getUsuario().getIdUsuario())
                .nombreCompleto(t.getUsuario().getNombre() + " " + t.getUsuario().getApellido())
                .celular(t.getUsuario().getCelular())
                .email(t.getUsuario().getEmail())
                .rol(t.getUsuario().getRol().name())
                .activo(t.getUsuario().getActivo())
                .fechaRegistro(t.getUsuario().getFechaRegistro())
                .build();

        VehiculoResponse vehiculoResponse = VehiculoResponse.builder()
                .idVehiculo(t.getVehiculo().getIdVehiculo())
                .placa(t.getVehiculo().getPlaca())
                .tipo(t.getVehiculo().getTipo().name())
                .propietario(t.getUsuario().getNombre() + " " + t.getUsuario().getApellido())
                .build();

        EspacioResponse espacioResponse = EspacioResponse.builder()
                .idEspacio(t.getEspacio().getIdEspacio())
                .codigo(t.getEspacio().getCodigo())
                .tipo(t.getEspacio().getTipo().name())
                .ocupado(t.getEspacio().isOcupado())
                .build();

        return AdminTicketResponse.builder()
                .idTicket(t.getIdTicket())
                .usuario(usuarioResponse)
                .vehiculo(vehiculoResponse)
                .espacio(espacioResponse)
                .fechaEntrada(t.getFechaEntrada())
                .fechaSalida(t.getFechaSalida())
                .estado(t.getEstado().name())
                .montoPago(montoOpt.orElse(null))
                .build();
    }
}
