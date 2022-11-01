package br.marcelojssantos.cloudparkingmanager.service;

import br.marcelojssantos.cloudparkingmanager.dto.EstacionamentoCreateDTO;
import br.marcelojssantos.cloudparkingmanager.model.Estacionamento;
import br.marcelojssantos.cloudparkingmanager.service.exception.EstacionamentoNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EstacionamentoService {

    private static Map<String, Estacionamento> estacionamentoMap = new HashMap();

    static {
        var id = getUUID('-');
        var licenca = getUUID('|');
        Estacionamento estacionamento = new Estacionamento(id,
                licenca,
                "RJ",
                "Fiat/Bravo",
                "Branca");
        estacionamentoMap.put(id, estacionamento);
        id = getUUID('-');
        licenca = getUUID('|');
        Estacionamento estacionamento2 = new Estacionamento(id,
                licenca,
                "SP",
                "VW/Gol",
                "Preta");
        estacionamentoMap.put(id, estacionamento2);
    }

    public List<Estacionamento> findAll(){
        return estacionamentoMap.values().stream().collect(Collectors.toList());
    }

    public Estacionamento findById(String id){
        var estacionamento = estacionamentoMap.get(id);
        if(estacionamento == null){
            throw new EstacionamentoNotFoundException(id);
        }
        return estacionamento;
    }

    public Estacionamento create(EstacionamentoCreateDTO estacionamentoCreateDTO) {
        var id = getUUID('-');
        Estacionamento estacionamento = new Estacionamento(id,
                estacionamentoCreateDTO.getLicenca(),
                estacionamentoCreateDTO.getEstado(),
                estacionamentoCreateDTO.getModelo(),
                estacionamentoCreateDTO.getCor());
        estacionamento.setDataEntrada(LocalDateTime.now());
        estacionamentoMap.put(id, estacionamento);
        return estacionamento;

    }

    private static String getUUID(Character caracter) {
        var uuid = UUID.randomUUID().toString().replace("-", caracter.toString());
        return uuid;
    }

}
