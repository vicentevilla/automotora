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

<<<<<<< HEAD
=======

>>>>>>> a6ed02af7276b2b2d3dd4413e2b7b68c13b1135c
import com.mcqueen.automotora.DTO.VehiculoRequestDTO;
import com.mcqueen.automotora.DTO.VehiculoResponseDTO;
import com.mcqueen.automotora.service.VehiculoService;

<<<<<<< HEAD
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
=======
>>>>>>> a6ed02af7276b2b2d3dd4413e2b7b68c13b1135c
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
<<<<<<< HEAD
@Tag(name = "Vehiculo", description = "Gestión de vehículos de la concesionaria")
=======
>>>>>>> a6ed02af7276b2b2d3dd4413e2b7b68c13b1135c
public class VehiculoController {

    private final VehiculoService vehiculoService;

<<<<<<< HEAD
    @Operation(summary = "Listar vehículos", description = "Retorna todos los vehículos registrados")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
=======
>>>>>>> a6ed02af7276b2b2d3dd4413e2b7b68c13b1135c
    @GetMapping 
    public ResponseEntity<List<VehiculoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(vehiculoService.obtenerTodos());
    }

<<<<<<< HEAD
    @Operation(summary = "Buscar vehículo por ID", description = "Retorna un vehículo según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Vehículo encontrado"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
=======
>>>>>>> a6ed02af7276b2b2d3dd4413e2b7b68c13b1135c
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return vehiculoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

<<<<<<< HEAD
    @Operation(summary = "Crear vehículo", description = "Registra un nuevo vehículo. La patente debe ser única y tener 6 caracteres")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Vehículo creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o patente duplicada")
    })
=======
>>>>>>> a6ed02af7276b2b2d3dd4413e2b7b68c13b1135c
    @PostMapping
    public ResponseEntity<VehiculoResponseDTO> crear(@Valid @RequestBody VehiculoRequestDTO vehiculo) {
        return ResponseEntity.status(201).body(vehiculoService.guardar(vehiculo));
    } 

<<<<<<< HEAD
    @Operation(summary = "Actualizar vehículo", description = "Actualiza los datos de un vehículo existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Vehículo actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
=======
>>>>>>> a6ed02af7276b2b2d3dd4413e2b7b68c13b1135c
    @PutMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> actualizar (@PathVariable Long id, @Valid @RequestBody VehiculoRequestDTO dto){
        return vehiculoService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

<<<<<<< HEAD
    @Operation(summary = "Eliminar vehículo", description = "Elimina un vehículo por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Vehículo eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
=======
>>>>>>> a6ed02af7276b2b2d3dd4413e2b7b68c13b1135c
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (vehiculoService.obtenerPorId(id).isEmpty()) return ResponseEntity.notFound().build();
        vehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

<<<<<<< HEAD
    @Operation(summary = "Buscar por marca", description = "Retorna vehículos filtrados por marca")
    @ApiResponse(responseCode = "200", description = "Lista filtrada correctamente")
=======
>>>>>>> a6ed02af7276b2b2d3dd4413e2b7b68c13b1135c
    @GetMapping("/buscar/{marca}")
    public ResponseEntity<List<VehiculoResponseDTO>> buscarPorMarca(@PathVariable String marca) {
        return ResponseEntity.ok(vehiculoService.buscarPorMarca(marca));
    }

<<<<<<< HEAD
    @Operation(summary = "Buscar por año", description = "Retorna vehículos desde el año indicado en adelante")
    @ApiResponse(responseCode = "200", description = "Lista filtrada correctamente")
=======
>>>>>>> a6ed02af7276b2b2d3dd4413e2b7b68c13b1135c
    @GetMapping("/anio-desde/{anio}")
    public ResponseEntity<List<VehiculoResponseDTO>> buscarPorAnioDesde(@PathVariable Integer anio){
        return ResponseEntity.ok(vehiculoService.buscarPorAnioDesde(anio));
    }

<<<<<<< HEAD
    @Operation(summary = "Buscar por tipo", description = "Retorna vehículos filtrados por tipo de vehículo")
    @ApiResponse(responseCode = "200", description = "Lista filtrada correctamente")
=======
>>>>>>> a6ed02af7276b2b2d3dd4413e2b7b68c13b1135c
    @GetMapping("/tipo/{tipoId}")
    public ResponseEntity<List<VehiculoResponseDTO>> buscarPorTipo(@PathVariable Long tipoId) {
        List<VehiculoResponseDTO> vehiculos = vehiculoService.buscarPorTipo(tipoId);
        if (vehiculos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vehiculos);
    }
    
<<<<<<< HEAD
=======
    
>>>>>>> a6ed02af7276b2b2d3dd4413e2b7b68c13b1135c

}
