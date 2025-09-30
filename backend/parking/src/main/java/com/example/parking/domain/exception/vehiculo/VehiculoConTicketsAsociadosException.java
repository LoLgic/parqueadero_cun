package com.example.parking.domain.exception.vehiculo;

import com.example.parking.domain.exception.BaseException;
import com.example.parking.domain.exception.ErrorCatalog;

public class VehiculoConTicketsAsociadosException extends BaseException {
	
	private static final long serialVersionUID = 1735868594625259518L;

	public VehiculoConTicketsAsociadosException(String message) {
        super(ErrorCatalog.VEHICULO_CON_TICKETS, message);
	}
}
