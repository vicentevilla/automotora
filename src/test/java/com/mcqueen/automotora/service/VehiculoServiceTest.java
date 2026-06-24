package com.mcqueen.automotora.service;

import com.mcqueen.automotora.DTO.VehiculoRequestDTO;
import com.mcqueen.automotora.DTO.VehiculoResponseDTO;
import com.mcqueen.automotora.model.TipoVehiculo;
import com.mcqueen.automotora.model.Vehiculo;
import com.mcqueen.automotora.Repository.TipoVehiculoRepository;
import com.mcqueen.automotora.Repository.VehiculoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class VehiculoServiceTest {

    @Autowired
    private VehiculoService vehiculoService;

    @MockBean
    private VehiculoRepository vehiculoRepository;

    @MockBean
    private TipoVehiculoRepository tipoVehiculoRepository;

    // ── HELPER ───────────────────────────────────────
    private Vehiculo crearVehiculo() {
        TipoVehiculo tipo = new TipoVehiculo(1L, "Sedan", "4 puertas");
        return new Vehiculo(1L, "AB1234", "Toyota", "Corolla", 2020, new BigDecimal("15000000"), tipo);
    }

    private VehiculoRequestDTO crearDTO() {
        return new VehiculoRequestDTO("AB1234", "Toyota", "Corolla", 2020, new BigDecimal("15000000"), 1L);
    }

    // ── TEST 1: obtener todos ─────────────────────────
    @Test
    public void testObtenerTodos() {
        Vehiculo vehiculo = crearVehiculo();
        when(vehiculoRepository.findAll()).thenReturn(List.of(vehiculo));

        List<VehiculoResponseDTO> resultado = vehiculoService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("AB1234", resultado.get(0).getPatente());
    }

    // ── TEST 2: obtener por id existente ──────────────
    @Test
    public void testObtenerPorIdExistente() {
        Vehiculo vehiculo = crearVehiculo();
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));

        Optional<VehiculoResponseDTO> resultado = vehiculoService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Toyota", resultado.get().getMarca());
    }

    // ── TEST 3: obtener por id inexistente ────────────
    @Test
    public void testObtenerPorIdInexistente() {
        when(vehiculoRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<VehiculoResponseDTO> resultado = vehiculoService.obtenerPorId(99L);

        assertFalse(resultado.isPresent());
    }

    // ── TEST 4: guardar vehículo nuevo ────────────────
    @Test
    public void testGuardarVehiculo() {
        VehiculoRequestDTO dto = crearDTO();
        TipoVehiculo tipo = new TipoVehiculo(1L, "Sedan", "4 puertas");
        Vehiculo vehiculo = crearVehiculo();

        when(vehiculoRepository.existsByPatente("AB1234")).thenReturn(false);
        when(tipoVehiculoRepository.findById(1L)).thenReturn(Optional.of(tipo));
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculo);

        VehiculoResponseDTO resultado = vehiculoService.guardar(dto);

        assertNotNull(resultado);
        assertEquals("AB1234", resultado.getPatente());
    }

    // ── TEST 5: guardar con patente duplicada ─────────
    @Test
    public void testGuardarPatenteDuplicada() {
        VehiculoRequestDTO dto = crearDTO();
        when(vehiculoRepository.existsByPatente("AB1234")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> vehiculoService.guardar(dto));
        assertTrue(ex.getMessage().contains("Ya existe un vehículo con la patente"));
    }

    // ── TEST 6: eliminar vehículo ─────────────────────
    @Test
    public void testEliminar() {
        doNothing().when(vehiculoRepository).deleteById(1L);

        vehiculoService.eliminar(1L);

        verify(vehiculoRepository, times(1)).deleteById(1L);
    }
}
