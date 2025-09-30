package com.example.parking.web.mapper.usuario;

import com.example.parking.domain.model.usuario.Rol;
import com.example.parking.domain.model.usuario.Usuario;
import com.example.parking.web.dto.usuario.AdminCreateUserRequest;
import com.example.parking.web.dto.usuario.RegistroClienteRequest;
import com.example.parking.web.dto.usuario.UsuarioResponse;

public class UsuarioMapper {

	public static Usuario fromRegistroCliente(RegistroClienteRequest req, String encodedPassword) {
        return Usuario.builder()
                .nombre(req.nombre())
                .apellido(req.apellido())
                .celular(req.celular())
                .email(req.email())
                .clave(encodedPassword)
                .rol(Rol.CLIENTE) // siempre CLIENTE
                .activo(true)
                .build();
    }
	
	public static Usuario fromAdminCreate(AdminCreateUserRequest req, String encodedPassword) {
        return Usuario.builder()
                .nombre(req.nombre())
                .apellido(req.apellido())
                .celular(req.celular())
                .email(req.email())
                .clave(encodedPassword)
                .rol(req.rol())
                .activo(true)
                .build();
    }
	
	public static UsuarioResponse toResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .idUsuario(usuario.getIdUsuario())
                .nombreCompleto(usuario.getNombre() + " " + usuario.getApellido())
                .celular(usuario.getCelular())
                .email(usuario.getEmail())
                .rol(usuario.getRol().name())
                .activo(usuario.getActivo())
                .fechaRegistro(usuario.getFechaRegistro())
                .build();
    }
}
