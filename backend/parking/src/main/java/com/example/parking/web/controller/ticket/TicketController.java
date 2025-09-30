package com.example.parking.web.controller.ticket;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.parking.domain.service.ticket.TicketService;
import com.example.parking.web.dto.ticket.AdminTicketResponse;
import com.example.parking.web.dto.ticket.TicketCreateRequest;
import com.example.parking.web.dto.ticket.TicketResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
	
	private final TicketService ticketService;
	
	// Crear ticket (CLIENTE/OPERADOR/ADMIN)
    @PostMapping
    @PreAuthorize("hasAnyRole('CLIENTE','OPERADOR','ADMIN')")
    public ResponseEntity <TicketResponse> crear(@Valid @RequestBody TicketCreateRequest req) {
    	TicketResponse response = ticketService.crear(req);
        return ResponseEntity.status(201).body(response);
    }
    
    // Mis tickets (CLIENTE)
    @GetMapping("/mios")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity <List<TicketResponse>> listarMios() {
    	List<TicketResponse> response = ticketService.listarMios();
        return ResponseEntity.ok(response);
    }
    
    // Listar todos (OPERADOR/ADMIN)
    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR','ADMIN')")
    public ResponseEntity <List<TicketResponse>> listarTodos() {
    	List<TicketResponse> response = ticketService.listarTodos();
        return ResponseEntity.ok(response);
    }
    
    // Obtener por id (OPERADOR/ADMIN) - útil para ver detalle
    @GetMapping("/{idTicket}")
    @PreAuthorize("hasAnyRole('OPERADOR','ADMIN')")
    public ResponseEntity<AdminTicketResponse> obtenerTicketPorId(@PathVariable Long idTicket) {
    	AdminTicketResponse response = ticketService.obtenerTicketPorId(idTicket);
        return ResponseEntity.ok(response);
    }
    
    // Finalizar ticket (OPERADOR/ADMIN) o el mismo CLIENTE si quieres habilitarlo
    @PutMapping("/{idTicket}/finalizar")
    @PreAuthorize("hasAnyRole('OPERADOR','ADMIN')")
    public ResponseEntity <TicketResponse> finalizar(@PathVariable Long idTicket) {
    	TicketResponse response = ticketService.finalizar(idTicket);
        return ResponseEntity.ok(response);
    }
    
}






