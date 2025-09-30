package com.example.parking.web.controller.usuario;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.parking.domain.model.usuario.Usuario;
import com.example.parking.domain.service.usuario.UsuarioService;
import com.example.parking.web.dto.usuario.AdminCreateUserRequest;
import com.example.parking.web.dto.usuario.UsuarioResponse;
import com.example.parking.web.mapper.usuario.UsuarioMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/usuarios")
@RequiredArgsConstructor
public class AdminUsuarioController {

	private final UsuarioService usuarioService;
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> crear(@Valid @RequestBody AdminCreateUserRequest req) {
        Usuario usuario = usuarioService.crearUsuarioPorAdmin(req);
        return ResponseEntity.status(201).body(UsuarioMapper.toResponse(usuario));
    }
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity <List<UsuarioResponse>> listar() {
    	List<UsuarioResponse> responses = usuarioService.listarTodos();
    	return ResponseEntity.ok(responses);
    }
}
