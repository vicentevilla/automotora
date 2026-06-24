package com.mcqueen.automotora.service;

import com.mcqueen.automotora.model.*;
import com.mcqueen.automotora.Repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class VentaServiceTest {

    @Autowired
    private VentaService ventaService;

    @MockBean
    private VentaRepository ventaRepository;

    @MockBean
    private VendedorRepository vendedorRepository;

    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private VehiculoRepository vehiculoRepository;

    // ── HELPER ───────────────────────────────────────
    private Venta crearVenta() {
        TipoVehiculo tipo = new TipoVehiculo(1L, "Sedan", "4 puertas");
        Vehiculo vehiculo = new Vehiculo(1L, "AB1234", "Toyota", "Corolla", 2020, new BigDecimal("15000000"), tipo);
        Vendedor vendedor = new Vendedor(1L, "Carlos", "12345678-9", "carlos@mail.cl", "+56912345678");
        Cliente cliente   = new Cliente(1L, "Pedro",   "11111111-1", "pedro@mail.cl",  "+56911111111");
        return new Venta(1L, LocalDate.now(), new BigDecimal("15000000"), "COMPLETADA", vendedor, cliente, vehiculo);
    }

    // ── TEST 1: obtener todas las ventas ──────────────
    @Test
    public void testObtenerTodos() {
        when(ventaRepository.findAll()).thenReturn(List.of(crearVenta()));

        List<Venta> resultado = ventaService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("COMPLETADA", resultado.get(0).getEstado());
    }

    // ── TEST 2: crear venta exitosa ───────────────────
    @Test
    public void testCrearVentaExitosa() {
        TipoVehiculo tipo = new TipoVehiculo(1L, "Sedan", "4 puertas");
        Vehiculo vehiculo = new Vehiculo(1L, "AB1234", "Toyota", "Corolla", 2020, new BigDecimal("15000000"), tipo);
        Vendedor vendedor = new Vendedor(1L, "Carlos", "12345678-9", "carlos@mail.cl", "+56912345678");
        Cliente cliente   = new Cliente(1L, "Pedro",   "11111111-1", "pedro@mail.cl",  "+56911111111");
        Venta venta       = crearVenta();

        when(vendedorRepository.findById(1L)).thenReturn(Optional.of(vendedor));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));
        when(ventaRepository.vehiculoYaVendido(1L)).thenReturn(false);
        when(ventaRepository.save(any(Venta.class))).thenReturn(venta);

        Venta resultado = ventaService.crear(1L, 1L, 1L);

        assertNotNull(resultado);
        assertEquals("COMPLETADA", resultado.getEstado());
        assertEquals(new BigDecimal("15000000"), resultado.getPrecioTotal());
    }

    // ── TEST 3: crear venta con vehículo ya vendido ───
    @Test
    public void testCrearVentaVehiculoYaVendido() {
        TipoVehiculo tipo = new TipoVehiculo(1L, "Sedan", "4 puertas");
        Vehiculo vehiculo = new Vehiculo(1L, "AB1234", "Toyota", "Corolla", 2020, new BigDecimal("15000000"), tipo);
        Vendedor vendedor = new Vendedor(1L, "Carlos", "12345678-9", "carlos@mail.cl", "+56912345678");
        Cliente cliente   = new Cliente(1L, "Pedro",   "11111111-1", "pedro@mail.cl",  "+56911111111");

        when(vendedorRepository.findById(1L)).thenReturn(Optional.of(vendedor));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));
        when(ventaRepository.vehiculoYaVendido(1L)).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> ventaService.crear(1L, 1L, 1L));
        assertTrue(ex.getMessage().contains("ya fue vendido"));
    }

    // ── TEST 4: anular venta ──────────────────────────
    @Test
    public void testAnularVenta() {
        Venta venta = crearVenta();
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));
        when(ventaRepository.save(any(Venta.class))).thenReturn(venta);

        Optional<Venta> resultado = ventaService.anular(1L);

        assertTrue(resultado.isPresent());
        assertEquals("ANULADA", resultado.get().getEstado());
    }
}
