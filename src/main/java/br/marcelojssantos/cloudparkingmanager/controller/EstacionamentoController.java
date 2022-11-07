package br.marcelojssantos.cloudparkingmanager.controller;

import br.marcelojssantos.cloudparkingmanager.dto.EstacionamentoCreateDTO;
import br.marcelojssantos.cloudparkingmanager.dto.EstacionamentoDTO;
import br.marcelojssantos.cloudparkingmanager.mapper.EstacionamentoMapper;
import br.marcelojssantos.cloudparkingmanager.service.EstacionamentoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estacionamentos")
@Api(tags = "EstacionamentoController")
public class EstacionamentoController {

    private final EstacionamentoService estacionamentoService;
    private EstacionamentoMapper estacionamentoMapper;

    public EstacionamentoController(EstacionamentoService estacionamentoService,
                                    EstacionamentoMapper estacionamentoMapper) {
        this.estacionamentoService = estacionamentoService;
        this.estacionamentoMapper = estacionamentoMapper;
    }

    @GetMapping
    @ApiOperation("Retorna todos estacionamentos")
    public ResponseEntity<List<EstacionamentoDTO>> findAll(){
        var listaEstacionamento = estacionamentoService.findAll();
        return ResponseEntity.ok(estacionamentoMapper.toListaEstacionamentoDTO(listaEstacionamento));
    }

    @GetMapping("/{id}")
    @ApiOperation("Retorna um estacionamento conforme o id passado")
    public ResponseEntity<EstacionamentoDTO> findById(@PathVariable String id){
        var estacionamento = estacionamentoService.findById(id);
        return ResponseEntity.ok(estacionamentoMapper.toEstacionamentoDTO(estacionamento));
    }

    @PostMapping
    @ApiOperation("Cria um novo estacionamento conforme dados passados")
    public ResponseEntity<EstacionamentoDTO> create(@RequestBody EstacionamentoCreateDTO estacionamentoCreateDTO){
        var estacionamento =estacionamentoService.create(estacionamentoMapper.toEstacionamentoCreate(estacionamentoCreateDTO));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(estacionamentoMapper.toEstacionamentoCreateDTO(estacionamento));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Deleta um estacionamento conforme o id passado")
    public ResponseEntity delete(@PathVariable String id){
        estacionamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualiza um estacionamento conforme dados passados")
    public ResponseEntity<EstacionamentoDTO> update(@PathVariable String id, @RequestBody EstacionamentoCreateDTO estacionamentoCreateDTO){
        var estacionamento =estacionamentoService
                .update(id, estacionamentoMapper.toEstacionamentoCreate(estacionamentoCreateDTO));
        return ResponseEntity.ok(estacionamentoMapper.toEstacionamentoCreateDTO(estacionamento));
    }

    @PostMapping("/{id}")
    @ApiOperation("Registra o fechamento de um estacionamento e calcula sua conta!")
    public ResponseEntity<EstacionamentoDTO> checkOut(@PathVariable String id){
        var estacionamento = estacionamentoService.checkOut(id);
        return ResponseEntity.ok(estacionamentoMapper.toEstacionamentoCreateDTO(estacionamento));
    }
}
