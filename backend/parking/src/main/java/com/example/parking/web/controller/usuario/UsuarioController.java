package com.example.parking.web.controller.usuario;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.parking.domain.model.usuario.Usuario;
import com.example.parking.domain.service.usuario.UsuarioService;
import com.example.parking.web.dto.usuario.UsuarioResponse;
import com.example.parking.web.mapper.usuario.UsuarioMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioService usuarioService;
	
	@GetMapping("/me")
	public ResponseEntity<UsuarioResponse> me(Authentication auth) {
		Usuario usuario = usuarioService.buscarPorEmail(auth.getName());
		return ResponseEntity.ok(UsuarioMapper.toResponse(usuario));
	 }	
}
