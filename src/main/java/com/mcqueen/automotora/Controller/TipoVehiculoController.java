package com.mcqueen.automotora.Controller;

import com.mcqueen.automotora.model.TipoVehiculo;
import com.mcqueen.automotora.service.TipoVehiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipovehiculos")
@RequiredArgsConstructor
@Tag(name = "TipoVehiculo", description = "Gestión de tipos de vehículo")
public class TipoVehiculoController {

     private final TipoVehiculoService tipoVehiculoService;

    @Operation(summary = "Listar tipos", description = "Retorna todos los tipos de vehículo registrados")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<TipoVehiculo>> obtenerTodos(){
        List<TipoVehiculo> tipos = tipoVehiculoService.obtenerTodos();
        if (tipos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tipos);
    }

    @Operation(summary = "Buscar tipo por ID", description = "Retorna un tipo de vehículo según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipo encontrado"),
        @ApiResponse(responseCode = "404", description = "Tipo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TipoVehiculo> obtenerPorId(@PathVariable Long id){
        Optional<TipoVehiculo> tipo = tipoVehiculoService.obtenerPorId(id);
        return tipo.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear tipo", description = "Registra un nuevo tipo de vehículo")
    @ApiResponse(responseCode = "201", description = "Tipo creado correctamente")
    @PostMapping
    public ResponseEntity<TipoVehiculo> guardar(@RequestBody TipoVehiculo tipoVehiculo){
         TipoVehiculo nuevoTipo = tipoVehiculoService.guardar(tipoVehiculo);
        return ResponseEntity.status(201).body(nuevoTipo);
    }

    @Operation(summary = "Actualizar tipo", description = "Actualiza los datos de un tipo de vehículo existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipo actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Tipo no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TipoVehiculo> actualizar(@PathVariable Long id,@RequestBody TipoVehiculo tipoVehiculo){
        Optional<TipoVehiculo> actualizado = tipoVehiculoService.actualizar(id, tipoVehiculo);
        return actualizado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar tipo", description = "Elimina un tipo de vehículo por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Tipo eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Tipo no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        if (tipoVehiculoService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        tipoVehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
