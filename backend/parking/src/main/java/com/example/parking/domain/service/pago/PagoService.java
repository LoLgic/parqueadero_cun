package com.example.parking.domain.service.pago;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.parking.domain.exception.usuario.UsuarioNoEncontradoException;
import com.example.parking.domain.model.usuario.Usuario;
import com.example.parking.domain.repo.pago.PagoRepository;
import com.example.parking.domain.repo.usuario.UsuarioRepository;
import com.example.parking.web.dto.pago.PagoResponse;
import com.example.parking.web.mapper.pago.PagoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagoService {

	
	private final PagoRepository pagoRepository;
    private final UsuarioRepository usuarioRepository;
    
    // Cliente: ver solo sus pagos
    @Transactional(readOnly = true)
    public List<PagoResponse> listarMisPagos(Authentication auth) {
        Usuario usuario = usuarioRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new UsuarioNoEncontradoException(
                		"Usuario no encontrado con email: " + auth.getName()));

        return pagoRepository.findByTicket_Usuario(usuario)
        		.stream()
                .map(PagoMapper::toResponse)
                .toList();
    }
    
    // Admin / Operador: ver todos los pagos
    @Transactional(readOnly = true)
    public List<PagoResponse> listarTodos() {
        return pagoRepository.findAll()
        		.stream()
                .map(PagoMapper::toResponse)
                .toList();
    }
}

