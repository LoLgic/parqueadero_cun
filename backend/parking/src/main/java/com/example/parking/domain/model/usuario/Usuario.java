package com.example.parking.domain.model.usuario;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




@Entity 
@Table(name = "usuarios")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class Usuario {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
    private Long idUsuario;

    @Column(nullable = false, unique = true, length = 20)
    private String celular;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name="password_hash", nullable = false, length = 255)
    private String clave; // hash BCrypt

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Rol rol;
    
    @Column(nullable = false)
    private Boolean activo = true;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void onCreate() {
        this.fechaRegistro = LocalDateTime.now();
    }

}
