package br.marcelojssantos.cloudparkingmanager.mapper;

import br.marcelojssantos.cloudparkingmanager.dto.EstacionamentoCreateDTO;
import br.marcelojssantos.cloudparkingmanager.dto.EstacionamentoDTO;
import br.marcelojssantos.cloudparkingmanager.model.Estacionamento;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstacionamentoMapper {

    private final ModelMapper MODEL_MAPPER = new ModelMapper();

    public EstacionamentoDTO toEstacionamentoDTO(Estacionamento estacionamento){
        return MODEL_MAPPER.map(estacionamento, EstacionamentoDTO.class);
    }

    public EstacionamentoDTO toEstacionamentoCreateDTO(Estacionamento estacionamento){
        return MODEL_MAPPER.map(estacionamento, EstacionamentoDTO.class);
    }

    public List<EstacionamentoDTO> toListaEstacionamentoDTO(List<Estacionamento> listaEstacionamento) {
        return listaEstacionamento.stream().map(this::toEstacionamentoDTO).collect(Collectors.toList());
    }

    public Estacionamento toEstacionamento(EstacionamentoDTO estacionamentoDTO){
        return MODEL_MAPPER.map(estacionamentoDTO, Estacionamento.class);
    }

    public List<Estacionamento> toListaEstacionamento(List<EstacionamentoDTO> listaEstacionamentoDTO) {
        return listaEstacionamentoDTO.stream().map(this::toEstacionamento).collect(Collectors.toList());
    }
}
