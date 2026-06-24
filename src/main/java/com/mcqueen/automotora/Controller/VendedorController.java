package com.mcqueen.automotora.Controller;

import com.mcqueen.automotora.model.Vendedor;
import com.mcqueen.automotora.service.VendedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendedores")
@RequiredArgsConstructor
@Tag(name = "Vendedor", description = "Gestión de vendedores de la concesionaria")
public class VendedorController {

    private final VendedorService vendedorService;

    @Operation(summary = "Listar vendedores", description = "Retorna todos los vendedores registrados")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<Vendedor>> obtenerTodos() {
        return ResponseEntity.ok(vendedorService.obtenerTodos());
    }

    @Operation(summary = "Buscar vendedor por ID", description = "Retorna un vendedor según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Vendedor encontrado"),
        @ApiResponse(responseCode = "404", description = "Vendedor no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Vendedor> obtenerPorId(@PathVariable Long id) {
        return vendedorService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear vendedor", description = "Registra un nuevo vendedor. El RUT debe ser único")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Vendedor creado correctamente"),
        @ApiResponse(responseCode = "400", description = "RUT duplicado u otros datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Vendedor> crear(@RequestBody Vendedor vendedor) {
        return ResponseEntity.status(201).body(vendedorService.guardar(vendedor));
    }

    @Operation(summary = "Actualizar vendedor", description = "Actualiza nombre, email y teléfono del vendedor")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Vendedor actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Vendedor no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Vendedor> actualizar(@PathVariable Long id, @RequestBody Vendedor datos) {
        return vendedorService.actualizar(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar vendedor", description = "Elimina un vendedor por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Vendedor eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Vendedor no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (vendedorService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        vendedorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
