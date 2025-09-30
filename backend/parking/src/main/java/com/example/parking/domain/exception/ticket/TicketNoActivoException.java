package com.example.parking.domain.exception.ticket;

import com.example.parking.domain.exception.BaseException;
import com.example.parking.domain.exception.ErrorCatalog;

public class TicketNoActivoException extends BaseException {
    
	private static final long serialVersionUID = -3437295438049404105L;

	public TicketNoActivoException() {
        super(ErrorCatalog.TICKET_NO_ACTIVO);
    }

    public TicketNoActivoException(String detalle) {
        super(ErrorCatalog.TICKET_NO_ACTIVO, detalle);
    }

}
