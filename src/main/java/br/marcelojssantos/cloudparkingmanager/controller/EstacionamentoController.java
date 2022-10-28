package br.marcelojssantos.cloudparkingmanager.controller;

import br.marcelojssantos.cloudparkingmanager.dto.EstacionamentoDTO;
import br.marcelojssantos.cloudparkingmanager.mapper.EstacionamentoMapper;
import br.marcelojssantos.cloudparkingmanager.model.Estacionamento;
import br.marcelojssantos.cloudparkingmanager.service.EstacionamentoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estacionamentos")
public class EstacionamentoController {

    private final EstacionamentoService estacionamentoService;
    private final EstacionamentoMapper estacionamentoMapper;

    public EstacionamentoController(EstacionamentoService estacionamentoService,
                                    EstacionamentoMapper estacionamentoMapper) {
        this.estacionamentoService = estacionamentoService;
        this.estacionamentoMapper = estacionamentoMapper;
    }

    @GetMapping
    public List<EstacionamentoDTO> findAll(){
        List<Estacionamento> listaEstacionamento = estacionamentoService.findAll();
        return estacionamentoMapper.toListaEstacionamentoDTO(listaEstacionamento);
    }
}
