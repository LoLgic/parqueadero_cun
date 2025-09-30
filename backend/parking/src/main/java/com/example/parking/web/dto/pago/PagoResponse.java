package com.example.parking.web.dto.pago;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record PagoResponse (
		Long idPago,
		BigDecimal monto,
        LocalDateTime fechaPago,
        Long ticketId,
        String tipo,
        String placaVehiculo,
        String nombre
        ){
}
