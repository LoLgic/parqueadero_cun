package com.example.parking.domain.exception.ticket;

import com.example.parking.domain.exception.BaseException;
import com.example.parking.domain.exception.ErrorCatalog;

public class TarifaNoDefinidaException extends BaseException {

	private static final long serialVersionUID = 2222233109156468156L;

	public TarifaNoDefinidaException(String detalle) {
        super(ErrorCatalog.TARIFA_NO_DEFINIDA, detalle);
    }

}
