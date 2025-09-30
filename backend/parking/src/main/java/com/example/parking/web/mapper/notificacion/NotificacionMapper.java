package com.example.parking.web.mapper.notificacion;

import com.example.parking.domain.model.notificacion.Notificacion;
import com.example.parking.web.dto.notificacion.NotificacionResponse;

public class NotificacionMapper {

	public static NotificacionResponse toResponse(Notificacion notif) {
        return NotificacionResponse.builder()
                .idNotificacion(notif.getIdNotificacion())
                .mensaje(notif.getMensaje())
                .fecha(notif.getFecha())
              //.estado(notif.getEstado().name())
                .build();
    }
}
