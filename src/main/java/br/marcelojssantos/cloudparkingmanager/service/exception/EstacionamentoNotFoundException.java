package br.marcelojssantos.cloudparkingmanager.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EstacionamentoNotFoundException extends RuntimeException {
    public EstacionamentoNotFoundException(String id) {
        super(String.format("Não há Estacionamento com ID '%s'!", id));
    }
}
