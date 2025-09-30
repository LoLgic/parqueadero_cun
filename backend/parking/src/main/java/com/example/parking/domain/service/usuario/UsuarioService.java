package com.example.parking.domain.service.usuario;

import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.parking.domain.exception.usuario.CredencialesInvalidasException;
import com.example.parking.domain.exception.usuario.UsuarioDuplicadoException;
import com.example.parking.domain.exception.usuario.UsuarioNoEncontradoException;
import com.example.parking.domain.model.usuario.Usuario;
import com.example.parking.domain.repo.usuario.UsuarioRepository;
import com.example.parking.security.TokenService;
import com.example.parking.web.dto.usuario.AdminCreateUserRequest;
import com.example.parking.web.dto.usuario.AuthResponse;
import com.example.parking.web.dto.usuario.RegistroClienteRequest;
import com.example.parking.web.dto.usuario.UsuarioResponse;
import com.example.parking.web.mapper.usuario.UsuarioMapper;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor
public class UsuarioService {

	
	private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Transactional
    public Usuario registrarCliente(RegistroClienteRequest req) { 
        validarUnicidad(req.email(), req.celular());

        Usuario usuario = UsuarioMapper.fromRegistroCliente(req, passwordEncoder.encode(req.clave()));
        return usuarioRepository.save(usuario);
    }
    
    @Transactional
    public Usuario crearUsuarioPorAdmin(AdminCreateUserRequest req) {
    	validarUnicidad(req.email(), req.celular());
    	
    	Usuario usuario = UsuarioMapper.fromAdminCreate(req, passwordEncoder.encode(req.clave()));
    	return usuarioRepository.save(usuario);
    }
  
    @Transactional(readOnly = true)
	public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNoEncontradoException("No existe un usuario con email: " + email));
    }

    @Transactional(readOnly = true)
	public List<UsuarioResponse> listarTodos() {
		return usuarioRepository.findAll()
				.stream()
				.map(UsuarioMapper::toResponse)
				.toList();
	}

    @Transactional(readOnly = true)
	public AuthResponse login(String email, String clave) {
		Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new CredencialesInvalidasException("Credenciales inválidas o usuario no encontrado"));
		
		if(!passwordEncoder.matches(clave, usuario.getClave()) || !Boolean.TRUE.equals(usuario.getActivo())) {
			throw new CredencialesInvalidasException("Credenciales inválidas o usuario inactivo");
		}
		
		String token = tokenService.generarToken(usuario);
		
		return new AuthResponse(token, usuario.getRol().name(), usuario.getIdUsuario(), usuario.getNombre());
	}
	
	private void validarUnicidad(String email, String cedula) {
   	 if (usuarioRepository.existsByEmail(email)) {
   		 throw new UsuarioDuplicadoException("El email ya está registrado");
   	 }
            
        if (usuarioRepository.existsByCelular(cedula)) {
       	 throw new UsuarioDuplicadoException("La  ya está registrado");	
        }
            
	}
}
