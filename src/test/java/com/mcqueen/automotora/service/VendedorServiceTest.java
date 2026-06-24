package com.mcqueen.automotora.service;

import com.mcqueen.automotora.model.Vendedor;
import com.mcqueen.automotora.Repository.VendedorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class VendedorServiceTest {

    @Autowired
    private VendedorService vendedorService;

    @MockBean
    private VendedorRepository vendedorRepository;

    // ── HELPER ───────────────────────────────────────
    private Vendedor crearVendedor() {
        return new Vendedor(1L, "Carlos Pérez", "12345678-9", "carlos@automotora.cl", "+56912345678");
    }

    // ── TEST 1: obtener todos ─────────────────────────
    @Test
    public void testObtenerTodos() {
        // Given
        when(vendedorRepository.findAll()).thenReturn(List.of(crearVendedor()));

        // When
        List<Vendedor> resultado = vendedorService.obtenerTodos();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Carlos Pérez", resultado.get(0).getNombre());
    }

    // ── TEST 2: obtener por id existente ──────────────
    @Test
    public void testObtenerPorIdExistente() {
        // Given
        when(vendedorRepository.findById(1L)).thenReturn(Optional.of(crearVendedor()));

        // When
        Optional<Vendedor> resultado = vendedorService.obtenerPorId(1L);

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("12345678-9", resultado.get().getRut());
    }

    // ── TEST 3: guardar con RUT duplicado ────────────
    @Test
    public void testGuardarRutDuplicado() {
        // Given
        Vendedor vendedor = crearVendedor();
        when(vendedorRepository.existsByRut("12345678-9")).thenReturn(true);

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> vendedorService.guardar(vendedor));
        assertTrue(ex.getMessage().contains("Ya existe un vendedor con el RUT"));
    }

    // ── TEST 4: guardar vendedor nuevo ────────────────
    @Test
    public void testGuardarVendedor() {
        // Given
        Vendedor vendedor = crearVendedor();
        when(vendedorRepository.existsByRut("12345678-9")).thenReturn(false);
        when(vendedorRepository.save(any(Vendedor.class))).thenReturn(vendedor);

        // When
        Vendedor resultado = vendedorService.guardar(vendedor);

        // Then
        assertNotNull(resultado);
        assertEquals("Carlos Pérez", resultado.getNombre());
    }

    // ── TEST 5: eliminar vendedor ─────────────────────
    @Test
    public void testEliminar() {
        // Given
        doNothing().when(vendedorRepository).deleteById(1L);

        // When
        vendedorService.eliminar(1L);

        // Then
        verify(vendedorRepository, times(1)).deleteById(1L);
    }
}
