package br.marcelojssantos.cloudparkingmanager.service;

import br.marcelojssantos.cloudparkingmanager.dto.EstacionamentoCreateDTO;
import br.marcelojssantos.cloudparkingmanager.mapper.EstacionamentoMapper;
import br.marcelojssantos.cloudparkingmanager.model.Estacionamento;
import br.marcelojssantos.cloudparkingmanager.service.exception.EstacionamentoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EstacionamentoService {

    private static Map<String, Estacionamento> estacionamentoMap = new HashMap();

//    static {
//        var id = getUUID('-');
//        var licenca = getUUID('|');
//        Estacionamento estacionamento = new Estacionamento(id,
//                licenca,
//                "RJ",
//                "Fiat/Bravo",
//                "Branca");
//        estacionamentoMap.put(id, estacionamento);
//        id = getUUID('-');
//        licenca = getUUID('|');
//        Estacionamento estacionamento2 = new Estacionamento(id,
//                licenca,
//                "SP",
//                "VW/Gol",
//                "Preta");
//        estacionamentoMap.put(id, estacionamento2);
//    }

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

    public void delete(String id){
        findById(id);
        estacionamentoMap.remove(id);
    }

    public Estacionamento update(String id, EstacionamentoCreateDTO estacionamentoCreateDTO) {
        Estacionamento estacionamento = findById(id);

        if(estacionamentoCreateDTO.getLicenca() != null)
            estacionamento.setLicenca(estacionamentoCreateDTO.getLicenca());
        if(estacionamentoCreateDTO.getEstado() != null)
            estacionamento.setEstado(estacionamentoCreateDTO.getEstado());
        if(estacionamentoCreateDTO.getModelo() != null)
            estacionamento.setModelo(estacionamentoCreateDTO.getModelo());
        if(estacionamentoCreateDTO.getCor() != null)
            estacionamento.setCor(estacionamentoCreateDTO.getCor());

        estacionamentoMap.replace(id, estacionamento);
        return estacionamento;
    }

    public Estacionamento exit(String id) {
        var estacionamento = findById(id);
        estacionamento.setDataSaida(LocalDateTime.now());
        estacionamento.setConta(calculaEstacionamento(estacionamento));
        estacionamentoMap.put(id, estacionamento);
        return estacionamento;
    }

    private static Double calculaEstacionamento(Estacionamento estacionamento) {
        //como alguns estacionamentos podem vir s/ a data de entrada
        if(estacionamento.getDataEntrada()==null)
            estacionamento.setDataEntrada(estacionamento.getDataSaida());
        //acrescenta sem 1 hora, mesmo se as horas forem zeradas
        var horas = 1 + ChronoUnit.HOURS
                .between(estacionamento.getDataEntrada(), estacionamento.getDataSaida());

        var valor = horas * 1.5;
        return valor;
    }

    private static String getUUID(Character caracter) {
        var uuid = UUID.randomUUID().toString().replace("-", caracter.toString());
        return uuid;
    }


}
