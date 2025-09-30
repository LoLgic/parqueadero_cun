package com.example.parking.domain.repo.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.parking.domain.model.usuario.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByCelular(String celular);
}
