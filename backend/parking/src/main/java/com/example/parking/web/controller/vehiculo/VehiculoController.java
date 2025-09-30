package com.example.parking.web.controller.vehiculo;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.parking.domain.service.vehiculo.VehiculoService;
import com.example.parking.web.dto.vehiculo.VehiculoRequest;
import com.example.parking.web.dto.vehiculo.VehiculoResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {

	private final VehiculoService vehiculoService;
	
	// Cliente registra su vehículo
    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity <VehiculoResponse> registrar(@RequestBody VehiculoRequest request, Authentication auth) {
        VehiculoResponse response = vehiculoService.registrar(request, auth);
        return ResponseEntity.status(201).body(response);
        
    }
    
    // Cliente lista solo sus vehículos
    @GetMapping("/mios")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity <List<VehiculoResponse>> listarMisVehiculos(Authentication auth) {
        List<VehiculoResponse> responses = vehiculoService.listarMisVehiculos(auth);
        return ResponseEntity.ok(responses);
    }
    
    // Admin ve todos los vehículos
    @GetMapping("/todos")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public ResponseEntity <List<VehiculoResponse>> listarTodos() {
        List<VehiculoResponse> responses = vehiculoService.listarTodos();
        return ResponseEntity.ok(responses);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN','OPERADOR')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id, Authentication auth) {
    	vehiculoService.eliminar(id, auth);
    	return ResponseEntity.noContent().build();
    }
    
}
