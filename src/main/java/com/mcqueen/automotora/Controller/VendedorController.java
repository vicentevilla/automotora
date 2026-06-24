package com.mcqueen.automotora.Controller;

import com.mcqueen.automotora.assembler.VendedorModelAssembler;
import com.mcqueen.automotora.model.Vendedor;
import com.mcqueen.automotora.service.VendedorService;
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
@RequestMapping("/api/vendedores")
@RequiredArgsConstructor
@Tag(name = "Vendedor", description = "Gestión de vendedores de la concesionaria")
public class VendedorController {

    private final VendedorService vendedorService;
    private final VendedorModelAssembler assembler;

    @Operation(summary = "Listar vendedores")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Vendedor>>> obtenerTodos() {
        List<EntityModel<Vendedor>> vendedores = vendedorService.obtenerTodos()
                .stream().map(assembler::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(vendedores,
                linkTo(methodOn(VendedorController.class).obtenerTodos()).withSelfRel()));
    }

    @Operation(summary = "Buscar vendedor por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Vendedor encontrado"),
        @ApiResponse(responseCode = "404", description = "Vendedor no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Vendedor>> obtenerPorId(@PathVariable Long id) {
        return vendedorService.obtenerPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear vendedor", description = "El RUT debe ser único")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Vendedor creado correctamente"),
        @ApiResponse(responseCode = "400", description = "RUT duplicado u otros datos inválidos")
    })
    @PostMapping
    public ResponseEntity<EntityModel<Vendedor>> crear(@RequestBody Vendedor vendedor) {
        return ResponseEntity.status(201).body(assembler.toModel(vendedorService.guardar(vendedor)));
    }

    @Operation(summary = "Actualizar vendedor")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Vendedor actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Vendedor no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Vendedor>> actualizar(@PathVariable Long id, @RequestBody Vendedor datos) {
        return vendedorService.actualizar(id, datos)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar vendedor")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Vendedor eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Vendedor no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (vendedorService.obtenerPorId(id).isEmpty()) return ResponseEntity.notFound().build();
        vendedorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}