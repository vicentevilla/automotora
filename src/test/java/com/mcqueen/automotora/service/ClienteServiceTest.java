package com.mcqueen.automotora.service;

import com.mcqueen.automotora.model.Cliente;
import com.mcqueen.automotora.Repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;

    @MockBean
    private ClienteRepository clienteRepository;

    // ── HELPER ───────────────────────────────────────
    private Cliente crearCliente() {
        return new Cliente(1L, "Pedro Rojas", "11111111-1", "pedro@gmail.com", "+56911111111");
    }

    // ── TEST 1: obtener todos ─────────────────────────
    @Test
    public void testObtenerTodos() {
        // Given
        when(clienteRepository.findAll()).thenReturn(List.of(crearCliente()));

        // When
        List<Cliente> resultado = clienteService.obtenerTodos();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Pedro Rojas", resultado.get(0).getNombre());
    }

    // ── TEST 2: obtener por id existente ──────────────
    @Test
    public void testObtenerPorIdExistente() {
        // Given
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(crearCliente()));

        // When
        Optional<Cliente> resultado = clienteService.obtenerPorId(1L);

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("11111111-1", resultado.get().getRut());
    }

    // ── TEST 3: obtener por id inexistente ────────────
    @Test
    public void testObtenerPorIdInexistente() {
        // Given
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        // When
        Optional<Cliente> resultado = clienteService.obtenerPorId(99L);

        // Then
        assertFalse(resultado.isPresent());
    }

    // ── TEST 4: guardar cliente nuevo ─────────────────
    @Test
    public void testGuardarCliente() {
        // Given
        Cliente cliente = crearCliente();
        when(clienteRepository.existsByRut("11111111-1")).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // When
        Cliente resultado = clienteService.guardar(cliente);

        // Then
        assertNotNull(resultado);
        assertEquals("Pedro Rojas", resultado.getNombre());
    }

    // ── TEST 5: guardar con RUT duplicado ────────────
    @Test
    public void testGuardarRutDuplicado() {
        // Given
        Cliente cliente = crearCliente();
        when(clienteRepository.existsByRut("11111111-1")).thenReturn(true);

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> clienteService.guardar(cliente));
        assertTrue(ex.getMessage().contains("Ya existe un cliente con el RUT"));
    }

    // ── TEST 6: actualizar cliente ────────────────────
    @Test
    public void testActualizarCliente() {
        // Given
        Cliente existente = crearCliente();
        Cliente nuevoDatos = new Cliente(null, "Pedro Actualizado", "11111111-1", "nuevo@gmail.com", "+56999999999");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(existente);

        // When
        Optional<Cliente> resultado = clienteService.actualizar(1L, nuevoDatos);

        // Then
        assertTrue(resultado.isPresent());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    // ── TEST 7: eliminar cliente ──────────────────────
    @Test
    public void testEliminar() {
        // Given
        doNothing().when(clienteRepository).deleteById(1L);

        // When
        clienteService.eliminar(1L);

        // Then
        verify(clienteRepository, times(1)).deleteById(1L);
    }
}
