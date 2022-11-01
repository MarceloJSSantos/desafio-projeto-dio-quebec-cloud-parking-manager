package br.marcelojssantos.cloudparkingmanager.controller;

import br.marcelojssantos.cloudparkingmanager.dto.EstacionamentoCreateDTO;
import br.marcelojssantos.cloudparkingmanager.dto.EstacionamentoDTO;
import br.marcelojssantos.cloudparkingmanager.mapper.EstacionamentoMapper;
import br.marcelojssantos.cloudparkingmanager.service.EstacionamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estacionamentos")
@Tag(name = "EstacionamentoController")
public class EstacionamentoController {

    private final EstacionamentoService estacionamentoService;
    private final EstacionamentoMapper estacionamentoMapper;

    public EstacionamentoController(EstacionamentoService estacionamentoService,
                                    EstacionamentoMapper estacionamentoMapper) {
        this.estacionamentoService = estacionamentoService;
        this.estacionamentoMapper = estacionamentoMapper;
    }

    @GetMapping
    @Operation(summary = "Retorna todos estacionamentos")
    public ResponseEntity<List<EstacionamentoDTO>> findAll(){
        var listaEstacionamento = estacionamentoService.findAll();
        return ResponseEntity.ok(estacionamentoMapper.toListaEstacionamentoDTO(listaEstacionamento));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna um estacionamento conforme o id passado")
    public ResponseEntity<EstacionamentoDTO> findById(@PathVariable String id){
        var estacionamento = estacionamentoService.findById(id);
        return ResponseEntity.ok(estacionamentoMapper.toEstacionamentoDTO(estacionamento));
    }

    @PostMapping
    @Operation(summary = "Cria um novo estacionamento conforme dados passados")
    public ResponseEntity<EstacionamentoDTO> create(@RequestBody EstacionamentoCreateDTO estacionamentoCreateDTO){
        var estacionamento =estacionamentoService.create(estacionamentoCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(estacionamentoMapper.toEstacionamentoCreateDTO(estacionamento));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um estacionamento conforme o id passado")
    public ResponseEntity delete(@PathVariable String id){
        estacionamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um estacionamento conforme dados passados")
    public ResponseEntity<EstacionamentoDTO> update(@PathVariable String id, @RequestBody EstacionamentoCreateDTO estacionamentoCreateDTO){
        var estacionamento =estacionamentoService.update(id, estacionamentoCreateDTO);
        return ResponseEntity.ok(estacionamentoMapper.toEstacionamentoCreateDTO(estacionamento));
    }

    @PostMapping("/{id}")
    @Operation(summary = "Registra o fechamento de um estacionamento e calcula sua conta!")
    public ResponseEntity<EstacionamentoDTO> exit(@PathVariable String id){
        var estacionamento = estacionamentoService.exit(id);
        return ResponseEntity.ok(estacionamentoMapper.toEstacionamentoCreateDTO(estacionamento));
    }
}
