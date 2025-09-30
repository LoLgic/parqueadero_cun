package com.example.parking.domain.exception.usuario;

import com.example.parking.domain.exception.BaseException;
import com.example.parking.domain.exception.ErrorCatalog;

public class CredencialesInvalidasException extends BaseException {

	private static final long serialVersionUID = -6757664265174801256L;

	public CredencialesInvalidasException(String detalle) {
        super(ErrorCatalog.CREDENCIALES_INVALIDAS, detalle);
    }

}
