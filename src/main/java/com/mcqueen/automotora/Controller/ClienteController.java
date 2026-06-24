package com.mcqueen.automotora.Controller;

import com.mcqueen.automotora.assembler.ClienteModelAssembler;
import com.mcqueen.automotora.model.Cliente;
import com.mcqueen.automotora.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Tag(name = "Cliente", description = "Gestión de clientes de la concesionaria")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteModelAssembler assembler;

    @Operation(summary = "Listar clientes")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Cliente>>> obtenerTodos() {
        List<EntityModel<Cliente>> clientes = clienteService.obtenerTodos()
                .stream().map(assembler::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(clientes,
                linkTo(methodOn(ClienteController.class).obtenerTodos()).withSelfRel()));
    }

    @Operation(summary = "Buscar cliente por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Cliente>> obtenerPorId(@PathVariable Long id) {
        return clienteService.obtenerPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear cliente", description = "El RUT debe ser único")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cliente creado correctamente"),
        @ApiResponse(responseCode = "400", description = "RUT duplicado u otros datos inválidos")
    })
    @PostMapping
    public ResponseEntity<EntityModel<Cliente>> crear(@RequestBody Cliente cliente) {
        return ResponseEntity.status(201).body(assembler.toModel(clienteService.guardar(cliente)));
    }

    @Operation(summary = "Actualizar cliente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Cliente>> actualizar(@PathVariable Long id, @RequestBody Cliente datos) {
        return clienteService.actualizar(id, datos)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar cliente")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (clienteService.obtenerPorId(id).isEmpty()) return ResponseEntity.notFound().build();
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
