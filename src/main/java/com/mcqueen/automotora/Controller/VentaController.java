package com.mcqueen.automotora.Controller;

import com.mcqueen.automotora.model.Venta;
import com.mcqueen.automotora.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
@Tag(name = "Venta", description = "Gestión de ventas de vehículos")
public class VentaController {

    private final VentaService ventaService;

    @Operation(summary = "Listar ventas", description = "Retorna todas las ventas registradas")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<Venta>> obtenerTodos() {
        return ResponseEntity.ok(ventaService.obtenerTodos());
    }

    @Operation(summary = "Buscar venta por ID", description = "Retorna una venta según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Venta encontrada"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerPorId(@PathVariable Long id) {
        return ventaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Crear venta",
        description = "Registra una venta. Body: { vendedorId, clienteId, vehiculoId }. " +
                      "Regla: un vehículo ya vendido no puede volver a venderse"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Venta creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Vehículo ya vendido o IDs inválidos")
    })
    @PostMapping
    public ResponseEntity<Venta> crear(@RequestBody Map<String, Long> body) {
        Long vendedorId = body.get("vendedorId");
        Long clienteId  = body.get("clienteId");
        Long vehiculoId = body.get("vehiculoId");
        return ResponseEntity.status(201).body(ventaService.crear(vendedorId, clienteId, vehiculoId));
    }

    @Operation(summary = "Anular venta", description = "Cambia el estado de una venta a ANULADA")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Venta anulada correctamente"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada"),
        @ApiResponse(responseCode = "400", description = "La venta ya estaba anulada")
    })
    @PutMapping("/{id}/anular")
    public ResponseEntity<Venta> anular(@PathVariable Long id) {
        return ventaService.anular(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar ventas por vendedor", description = "Retorna todas las ventas de un vendedor")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping("/vendedor/{vendedorId}")
    public ResponseEntity<List<Venta>> buscarPorVendedor(@PathVariable Long vendedorId) {
        return ResponseEntity.ok(ventaService.buscarPorVendedor(vendedorId));
    }

    @Operation(summary = "Buscar ventas por cliente", description = "Retorna todas las ventas de un cliente")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Venta>> buscarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(ventaService.buscarPorCliente(clienteId));
    }

    @Operation(summary = "Eliminar venta", description = "Elimina una venta por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Venta eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (ventaService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ventaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
