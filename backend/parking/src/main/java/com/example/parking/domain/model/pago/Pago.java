package com.example.parking.domain.model.pago;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.parking.domain.model.ticket.Ticket;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pagos")
@Getter @Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class Pago {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pago")
    private Long idPago;

    @OneToOne
    @JoinColumn(name = "id_ticket", nullable = false, unique = true)
    private Ticket ticket;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;
}
