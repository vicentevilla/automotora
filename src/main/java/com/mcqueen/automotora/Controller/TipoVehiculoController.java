package com.mcqueen.automotora.Controller;

import com.mcqueen.automotora.model.TipoVehiculo;
import com.mcqueen.automotora.service.TipoVehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipovehiculos")
@RequiredArgsConstructor
public class TipoVehiculoController {

     private final TipoVehiculoService tipoVehiculoService;

    @GetMapping
    public ResponseEntity<List<TipoVehiculo>> obtenerTodos(){
        List<TipoVehiculo> tipos = tipoVehiculoService.obtenerTodos();
        if (tipos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoVehiculo> obtenerPorId(@PathVariable Long id){
        Optional<TipoVehiculo> tipo = tipoVehiculoService.obtenerPorId(id);
        return tipo.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
    @PostMapping
    public ResponseEntity<TipoVehiculo> guardar(@RequestBody TipoVehiculo tipoVehiculo){
         TipoVehiculo nuevoTipo = tipoVehiculoService.guardar(tipoVehiculo);
        return ResponseEntity.ok(nuevoTipo);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<TipoVehiculo> actualizar(@PathVariable Long id,@RequestBody TipoVehiculo tipoVehiculo){
        Optional<TipoVehiculo> actualizado = tipoVehiculoService.actualizar(id, tipoVehiculo);
        return actualizado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        tipoVehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
