package com.example.parking.web.controller.pago;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.parking.domain.service.pago.PagoService;
import com.example.parking.web.dto.pago.PagoResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

	private final PagoService pagoService;
    
	// Cliente: ver solo sus pagos
    @GetMapping("/mios")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity <List<PagoResponse>> misPagos(Authentication auth) {
    	List<PagoResponse> response = pagoService.listarMisPagos(auth);
        return ResponseEntity.ok(response);
    }
    
    // Admin y Operador: ver todos los pagos
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public ResponseEntity <List<PagoResponse>> listarTodos() {
    	List<PagoResponse> response = pagoService.listarTodos();
        return ResponseEntity.ok(response);
    }
}
