package br.marcelojssantos.cloudparkingmanager.service;

import br.marcelojssantos.cloudparkingmanager.dto.EstacionamentoDTO;
import br.marcelojssantos.cloudparkingmanager.model.Estacionamento;
import org.springframework.stereotype.Service;

import java.util.*;
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
        return estacionamentoMap.get(id);
    }

    public Estacionamento create(EstacionamentoDTO estacionamentoDTO) {
        var id = getUUID('-');
        var licenca = getUUID('|');
        Estacionamento estacionamento = new Estacionamento(id,
                licenca,
                estacionamentoDTO.getEstado(),
                estacionamentoDTO.getModelo(),
                estacionamentoDTO.getCor());
        estacionamentoMap.put(id, estacionamento);
        return estacionamento;

    }

    private static String getUUID(Character caracter) {
        var uuid = UUID.randomUUID().toString().replace("-", caracter.toString());
        return uuid;
    }

}