package com.example.parking.domain.exception.usuario;

import com.example.parking.domain.exception.BaseException;
import com.example.parking.domain.exception.ErrorCatalog;

public class UsuarioDuplicadoException extends BaseException {

	private static final long serialVersionUID = -5679903635959561209L;

	public UsuarioDuplicadoException(String detalle) {
        super(ErrorCatalog.USUARIO_DUPLICADO, detalle);
    }

}
