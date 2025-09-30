package com.example.parking.web.controller.espacio;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.parking.domain.model.vehiculo.TipoVehiculo;
import com.example.parking.domain.service.espacio.EspacioService;
import com.example.parking.web.dto.espacio.EspacioRequest;
import com.example.parking.web.dto.espacio.EspacioResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/espacios")
@RequiredArgsConstructor
public class EspacioController {

	
	private final EspacioService espacioService;
	
	// ADMIN: crear nuevo espacio
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity <EspacioResponse> crear(@RequestBody EspacioRequest request) {
    	EspacioResponse response = espacioService.crear(request);
        return ResponseEntity.status(201).body(response);
    }
    
    
    // ADMIN y OPERADOR: listar todos
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR','CLIENTE')")
    public ResponseEntity <List<EspacioResponse>> listarTodos() {
    	List<EspacioResponse> response = espacioService.listarTodos();
        return ResponseEntity.ok(response);
    }
    
    // ADMIN y OPERADOR: listar espacios libres por tipo
    @GetMapping("/libres/{tipo}")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR','CLIENTE')")
    public ResponseEntity <List<EspacioResponse>> listarLibresPorTipo(@PathVariable TipoVehiculo tipo) {
    	List<EspacioResponse> response = espacioService.listarLibresPorTipo(tipo);
        return ResponseEntity.ok(response);
    }
    
    // OPERADOR: actualizar estado ocupado/libre
    @PutMapping("/{idEspacio}/estado")
    @PreAuthorize("hasAnyRole('ADMIN','OPERADOR')")
    public ResponseEntity <EspacioResponse> actualizarEstado(@PathVariable Long idEspacio, @RequestParam boolean ocupado) {
    	EspacioResponse response = espacioService.actualizarEstado(idEspacio, ocupado);
    	return ResponseEntity.ok(response);
    }
}
