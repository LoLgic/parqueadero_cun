package com.example.parking.web.controller.notificacion;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.parking.domain.service.notificacion.NotificacionService;
import com.example.parking.web.dto.notificacion.NotificacionResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

	private final NotificacionService notificacionService;

    @GetMapping("/mias")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<List<NotificacionResponse>> listarMisNotificaciones(Authentication auth) {
    	List <NotificacionResponse> response = notificacionService.listarMisNotificaciones(auth);
        return ResponseEntity.ok(response);
    }
}
