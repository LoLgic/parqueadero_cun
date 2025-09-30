package com.example.parking.web.controller.usuario;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.parking.domain.model.usuario.Usuario;
import com.example.parking.domain.service.usuario.UsuarioService;
import com.example.parking.web.dto.usuario.AuthResponse;
import com.example.parking.web.dto.usuario.LoginRequest;
import com.example.parking.web.dto.usuario.RegistroClienteRequest;
import com.example.parking.web.dto.usuario.UsuarioResponse;
import com.example.parking.web.mapper.usuario.UsuarioMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	
	private final UsuarioService usuarioService;
    
    
    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> register(@Valid @RequestBody RegistroClienteRequest  req) {
    	Usuario usuario = usuarioService.registrarCliente(req);	
    	return ResponseEntity.status(201).body(UsuarioMapper.toResponse(usuario));
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
    	return ResponseEntity.ok(usuarioService.login(req.email(), req.clave()));
    }
}
