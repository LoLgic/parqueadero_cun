package com.example.parking.domain.exception.ticket;

import com.example.parking.domain.exception.BaseException;
import com.example.parking.domain.exception.ErrorCatalog;

public class AccesoNoPermitidoException extends BaseException {
  
	private static final long serialVersionUID = -1317313407218245290L;

	public AccesoNoPermitidoException(String detalle) {
        super(ErrorCatalog.ACCESO_NO_PERMITIDO, detalle);
    }

}
