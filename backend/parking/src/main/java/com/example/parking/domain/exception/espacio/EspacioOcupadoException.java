package com.example.parking.domain.exception.espacio;

import com.example.parking.domain.exception.BaseException;
import com.example.parking.domain.exception.ErrorCatalog;

public class EspacioOcupadoException extends BaseException {
    
	private static final long serialVersionUID = 8083559446739238303L;

	public EspacioOcupadoException() {
        super(ErrorCatalog.ESPACIO_OCUPADO);
    }

    public EspacioOcupadoException(String detalle) {
        super(ErrorCatalog.ESPACIO_OCUPADO, detalle);
    }

}
