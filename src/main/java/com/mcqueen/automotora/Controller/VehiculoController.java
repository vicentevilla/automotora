package com.mcqueen.automotora.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mcqueen.automotora.DTO.VehiculoRequestDTO;
import com.mcqueen.automotora.DTO.VehiculoResponseDTO;
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

    @Operation(summary = "Listar vehículos", description = "Retorna todos los vehículos registrados")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping 
    public ResponseEntity<List<VehiculoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(vehiculoService.obtenerTodos());
    }

    @Operation(summary = "Buscar vehículo por ID", description = "Retorna un vehículo según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Vehículo encontrado"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return vehiculoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear vehículo", description = "Registra un nuevo vehículo. La patente debe ser única y tener 6 caracteres")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Vehículo creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o patente duplicada")
    })
    @PostMapping
    public ResponseEntity<VehiculoResponseDTO> crear(@Valid @RequestBody VehiculoRequestDTO vehiculo) {
        return ResponseEntity.status(201).body(vehiculoService.guardar(vehiculo));
    } 

    @Operation(summary = "Actualizar vehículo", description = "Actualiza los datos de un vehículo existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Vehículo actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> actualizar (@PathVariable Long id, @Valid @RequestBody VehiculoRequestDTO dto){
        return vehiculoService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar vehículo", description = "Elimina un vehículo por su ID")
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

    @Operation(summary = "Buscar por marca", description = "Retorna vehículos filtrados por marca")
    @ApiResponse(responseCode = "200", description = "Lista filtrada correctamente")
    @GetMapping("/buscar/{marca}")
    public ResponseEntity<List<VehiculoResponseDTO>> buscarPorMarca(@PathVariable String marca) {
        return ResponseEntity.ok(vehiculoService.buscarPorMarca(marca));
    }

    @Operation(summary = "Buscar por año", description = "Retorna vehículos desde el año indicado en adelante")
    @ApiResponse(responseCode = "200", description = "Lista filtrada correctamente")
    @GetMapping("/anio-desde/{anio}")
    public ResponseEntity<List<VehiculoResponseDTO>> buscarPorAnioDesde(@PathVariable Integer anio){
        return ResponseEntity.ok(vehiculoService.buscarPorAnioDesde(anio));
    }

    @Operation(summary = "Buscar por tipo", description = "Retorna vehículos filtrados por tipo de vehículo")
    @ApiResponse(responseCode = "200", description = "Lista filtrada correctamente")
    @GetMapping("/tipo/{tipoId}")
    public ResponseEntity<List<VehiculoResponseDTO>> buscarPorTipo(@PathVariable Long tipoId) {
        List<VehiculoResponseDTO> vehiculos = vehiculoService.buscarPorTipo(tipoId);
        if (vehiculos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vehiculos);
    }
    

}
