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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {

    private final VehiculoService vehiculoService;

    @GetMapping 
    public ResponseEntity<List<VehiculoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(vehiculoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return vehiculoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VehiculoResponseDTO> crear(@Valid @RequestBody VehiculoRequestDTO vehiculo) {
        return ResponseEntity.status(201).body(vehiculoService.guardar(vehiculo));
    } 

    @PutMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> actualizar (@PathVariable Long id, @Valid @RequestBody VehiculoRequestDTO dto){
        return vehiculoService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (vehiculoService.obtenerPorId(id).isEmpty()) return ResponseEntity.notFound().build();
        vehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/{marca}")
    public ResponseEntity<List<VehiculoResponseDTO>> buscarPorMarca(@PathVariable String marca) {
        return ResponseEntity.ok(vehiculoService.buscarPorMarca(marca));
    }

    @GetMapping("/anio-desde/{anio}")
    public ResponseEntity<List<VehiculoResponseDTO>> buscarPorAnioDesde(@PathVariable Integer anio){
        return ResponseEntity.ok(vehiculoService.buscarPorAnioDesde(anio));
    }

    @GetMapping("/tipo/{tipoId}")
    public ResponseEntity<List<VehiculoResponseDTO>> buscarPorTipo(@PathVariable Long tipoId) {
        List<VehiculoResponseDTO> vehiculos = vehiculoService.buscarPorTipo(tipoId);
        if (vehiculos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vehiculos);
    }
    
    

}
