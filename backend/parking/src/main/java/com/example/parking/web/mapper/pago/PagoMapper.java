package com.example.parking.web.mapper.pago;

import com.example.parking.domain.model.pago.Pago;
import com.example.parking.web.dto.pago.PagoResponse;

public class PagoMapper {

	public static PagoResponse toResponse(Pago pago) {
        return PagoResponse.builder()
                .idPago(pago.getIdPago())
                .monto(pago.getMonto())
                .fechaPago(pago.getFechaPago())
                .ticketId(pago.getTicket().getIdTicket())
                .tipo(pago.getTicket().getVehiculo().getTipo().name())
                .placaVehiculo(pago.getTicket().getVehiculo().getPlaca())
                .nombre(pago.getTicket().getUsuario().getNombre()
                        + " " + pago.getTicket().getUsuario().getApellido())
                .build();
    }
}
