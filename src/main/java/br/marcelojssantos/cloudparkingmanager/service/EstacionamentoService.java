package br.marcelojssantos.cloudparkingmanager.service;

import br.marcelojssantos.cloudparkingmanager.model.Estacionamento;
import br.marcelojssantos.cloudparkingmanager.repository.EstacionamentoRepository;
import br.marcelojssantos.cloudparkingmanager.service.exception.EstacionamentoNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class EstacionamentoService {

    private final EstacionamentoRepository estacionamentoRepository;

    public EstacionamentoService(EstacionamentoRepository estacionamentoRepository) {
        this.estacionamentoRepository = estacionamentoRepository;
    }

    @Transactional(readOnly = true)
    public List<Estacionamento> findAll(){
        return  estacionamentoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Estacionamento findById(String id){
        var estacionamento = estacionamentoRepository
                .findById(id)
                .orElseThrow(()-> new EstacionamentoNotFoundException(id));
        return estacionamento;
    }

    @Transactional
    public Estacionamento create(Estacionamento estacionamento) {
        var id = getUUID('-');
        estacionamento.setId(id);
        estacionamento.setDataEntrada(LocalDateTime.now());
        estacionamentoRepository.save(estacionamento);
        return estacionamento;

    }

    @Transactional
    public void delete(String id){
        findById(id);
        estacionamentoRepository.deleteById(id);
    }

    @Transactional
    public Estacionamento update(String id, Estacionamento estacionamentoAtualizado) {
        Estacionamento estacionamento = findById(id);

        if(estacionamentoAtualizado.getLicenca() != null)
            estacionamento.setLicenca(estacionamentoAtualizado.getLicenca());
        if(estacionamentoAtualizado.getEstado() != null)
            estacionamento.setEstado(estacionamentoAtualizado.getEstado());
        if(estacionamentoAtualizado.getModelo() != null)
            estacionamento.setModelo(estacionamentoAtualizado.getModelo());
        if(estacionamentoAtualizado.getCor() != null)
            estacionamento.setCor(estacionamentoAtualizado.getCor());

        estacionamentoRepository.save(estacionamento);
        return estacionamento;
    }

    @Transactional
    public Estacionamento checkOut(String id) {
        var estacionamento = findById(id);
        estacionamento.setDataSaida(LocalDateTime.now());
        estacionamento.setConta(calculaEstacionamento(estacionamento));
        estacionamentoRepository.save(estacionamento);
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
