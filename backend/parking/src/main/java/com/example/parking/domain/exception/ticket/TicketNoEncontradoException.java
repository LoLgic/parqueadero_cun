package com.example.parking.domain.exception.ticket;

import com.example.parking.domain.exception.BaseException;
import com.example.parking.domain.exception.ErrorCatalog;

public class TicketNoEncontradoException extends BaseException {

	private static final long serialVersionUID = 139131305903682221L;

	public TicketNoEncontradoException(String detalle) {
        super(ErrorCatalog.TICKET_NO_ENCONTRADO, detalle);
    }

}
