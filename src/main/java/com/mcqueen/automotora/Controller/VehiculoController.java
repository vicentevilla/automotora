package com.mcqueen.automotora.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.mcqueen.automotora.DTO.VehiculoRequestDTO;
import com.mcqueen.automotora.DTO.VehiculoResponseDTO;
import com.mcqueen.automotora.assembler.VehiculoModelAssembler;
import com.mcqueen.automotora.service.VehiculoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
@Tag(name = "Vehiculo", description = "Gestión de vehículos de la concesionaria")
public class VehiculoController {

    private final VehiculoService vehiculoService;
    private final VehiculoModelAssembler assembler;

    @Operation(summary = "Listar vehículos", description = "Retorna todos los vehículos registrados")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<VehiculoResponseDTO>>> obtenerTodos() {
        List<EntityModel<VehiculoResponseDTO>> vehiculos = vehiculoService.obtenerTodos()
                .stream().map(assembler::toModel).collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(vehiculos,
                linkTo(methodOn(VehiculoController.class).obtenerTodos()).withSelfRel()));
    }

    @Operation(summary = "Buscar vehículo por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Vehículo encontrado"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<VehiculoResponseDTO>> obtenerPorId(@PathVariable Long id) {
        return vehiculoService.obtenerPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear vehículo", description = "La patente debe ser única y tener 6 caracteres")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Vehículo creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o patente duplicada")
    })
    @PostMapping
    public ResponseEntity<EntityModel<VehiculoResponseDTO>> crear(@Valid @RequestBody VehiculoRequestDTO dto) {
        EntityModel<VehiculoResponseDTO> model = assembler.toModel(vehiculoService.guardar(dto));
        return ResponseEntity.status(201).body(model);
    }

    @Operation(summary = "Actualizar vehículo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Vehículo actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<VehiculoResponseDTO>> actualizar(@PathVariable Long id,
            @Valid @RequestBody VehiculoRequestDTO dto) {
        return vehiculoService.actualizar(id, dto)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar vehículo")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Vehículo eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (vehiculoService.obtenerPorId(id).isEmpty()) return ResponseEntity.notFound().build();
        vehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar por marca")
    @ApiResponse(responseCode = "200", description = "Lista filtrada correctamente")
    @GetMapping("/buscar/{marca}")
    public ResponseEntity<CollectionModel<EntityModel<VehiculoResponseDTO>>> buscarPorMarca(@PathVariable String marca) {
        List<EntityModel<VehiculoResponseDTO>> vehiculos = vehiculoService.buscarPorMarca(marca)
                .stream().map(assembler::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(vehiculos,
                linkTo(methodOn(VehiculoController.class).buscarPorMarca(marca)).withSelfRel()));
    }

    @Operation(summary = "Buscar por año desde")
    @ApiResponse(responseCode = "200", description = "Lista filtrada correctamente")
    @GetMapping("/anio-desde/{anio}")
    public ResponseEntity<CollectionModel<EntityModel<VehiculoResponseDTO>>> buscarPorAnioDesde(@PathVariable Integer anio) {
        List<EntityModel<VehiculoResponseDTO>> vehiculos = vehiculoService.buscarPorAnioDesde(anio)
                .stream().map(assembler::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(vehiculos,
                linkTo(methodOn(VehiculoController.class).buscarPorAnioDesde(anio)).withSelfRel()));
    }

    @Operation(summary = "Buscar por tipo")
    @ApiResponse(responseCode = "200", description = "Lista filtrada correctamente")
    @GetMapping("/tipo/{tipoId}")
    public ResponseEntity<CollectionModel<EntityModel<VehiculoResponseDTO>>> buscarPorTipo(@PathVariable Long tipoId) {
        List<EntityModel<VehiculoResponseDTO>> vehiculos = vehiculoService.buscarPorTipo(tipoId)
                .stream().map(assembler::toModel).collect(Collectors.toList());
        if (vehiculos.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(CollectionModel.of(vehiculos,
                linkTo(methodOn(VehiculoController.class).buscarPorTipo(tipoId)).withSelfRel()));
    }
}
