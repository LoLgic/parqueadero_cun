package com.example.parking.domain.service.notificacion;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.parking.domain.exception.usuario.UsuarioNoEncontradoException;
import com.example.parking.domain.model.usuario.Usuario;
import com.example.parking.domain.repo.notificacion.NotificacionRepository;
import com.example.parking.domain.repo.usuario.UsuarioRepository;
import com.example.parking.web.dto.notificacion.NotificacionResponse;
import com.example.parking.web.mapper.notificacion.NotificacionMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificacionService {
	
	private final NotificacionRepository notificacionRepository;
	private final UsuarioRepository usuarioRepository;
	

	// Listar notificaciones del usuario autenticado
	@Transactional(readOnly = true)
	public List<NotificacionResponse> listarMisNotificaciones(Authentication auth) {
		Usuario usuario = usuarioRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new UsuarioNoEncontradoException(
                		"Usuario no encontrado con email: " + auth.getName()));
		
		
		return notificacionRepository.findByUsuarioOrderByFechaDesc(usuario)
                .stream()
                .map(NotificacionMapper::toResponse)
                .toList();
	}
}
