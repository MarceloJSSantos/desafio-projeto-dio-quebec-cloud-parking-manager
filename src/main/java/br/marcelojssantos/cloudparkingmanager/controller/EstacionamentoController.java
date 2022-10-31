package br.marcelojssantos.cloudparkingmanager.controller;

import br.marcelojssantos.cloudparkingmanager.dto.EstacionamentoCreateDTO;
import br.marcelojssantos.cloudparkingmanager.dto.EstacionamentoDTO;
import br.marcelojssantos.cloudparkingmanager.mapper.EstacionamentoMapper;
import br.marcelojssantos.cloudparkingmanager.model.Estacionamento;
import br.marcelojssantos.cloudparkingmanager.service.EstacionamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<EstacionamentoDTO>> findAll(){
        var listaEstacionamento = estacionamentoService.findAll();
        return ResponseEntity.ok(estacionamentoMapper.toListaEstacionamentoDTO(listaEstacionamento));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstacionamentoDTO> findById(@PathVariable String id){
        var estacionamento = estacionamentoService.findById(id);
        return ResponseEntity.ok(estacionamentoMapper.toEstacionamentoDTO(estacionamento));
    }

    @PostMapping
    public ResponseEntity<EstacionamentoDTO> create(@RequestBody EstacionamentoCreateDTO estacionamentoCreateDTO){
        var estacionamento =estacionamentoService.create(estacionamentoCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(estacionamentoMapper.toEstacionamentoCreateDTO(estacionamento));
    }
}
