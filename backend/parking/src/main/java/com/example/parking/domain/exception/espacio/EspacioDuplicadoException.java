package com.example.parking.domain.exception.espacio;

import com.example.parking.domain.exception.BaseException;
import com.example.parking.domain.exception.ErrorCatalog;

public class EspacioDuplicadoException extends BaseException {

	private static final long serialVersionUID = -4535106703431584661L;

	public EspacioDuplicadoException(String detalle) {
        super(ErrorCatalog.ESPACIO_DUPLICADO, detalle);
    }

}
