package com.mcqueen.automotora.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.mcqueen.automotora.Controller.VehiculoController;
import com.mcqueen.automotora.DTO.VehiculoRequestDTO;
import com.mcqueen.automotora.DTO.VehiculoResponseDTO;
import com.mcqueen.automotora.assembler.VehiculoModelAssembler;
import com.mcqueen.automotora.service.VehiculoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@WebMvcTest(VehiculoController.class)
@Import(VehiculoModelAssembler.class)
public class VehiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehiculoService vehiculoService;

    @Autowired
    private ObjectMapper objectMapper;

    private VehiculoResponseDTO vehiculoDTO;
    private VehiculoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        vehiculoDTO = new VehiculoResponseDTO();
        vehiculoDTO.setId(1L);
        vehiculoDTO.setPatente("AB1234");
        vehiculoDTO.setMarca("Toyota");
        vehiculoDTO.setModelo("Corolla");
        vehiculoDTO.setAnio(2020);
        vehiculoDTO.setPrecio(new BigDecimal("15000000"));

        requestDTO = new VehiculoRequestDTO("AB1234", "Toyota", "Corolla", 2020, new BigDecimal("15000000"), 1L);
    }

    @Test
    public void testObtenerTodos() throws Exception {
        // Given
        when(vehiculoService.obtenerTodos()).thenReturn(List.of(vehiculoDTO));

        // When & Then
        mockMvc.perform(get("/api/vehiculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.vehiculoResponseDTOList[0].patente").value("AB1234"))
                .andExpect(jsonPath("$._embedded.vehiculoResponseDTOList[0].marca").value("Toyota"));
    }

    @Test
    public void testObtenerPorId() throws Exception {
        // Given
        when(vehiculoService.obtenerPorId(1L)).thenReturn(Optional.of(vehiculoDTO));

        // When & Then
        mockMvc.perform(get("/api/vehiculos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patente").value("AB1234"))
                .andExpect(jsonPath("$.marca").value("Toyota"));
    }

    @Test
    public void testObtenerPorIdNoEncontrado() throws Exception {
        // Given
        when(vehiculoService.obtenerPorId(99L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/vehiculos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCrear() throws Exception {
        // Given
        when(vehiculoService.guardar(any(VehiculoRequestDTO.class))).thenReturn(vehiculoDTO);

        // When & Then
        mockMvc.perform(post("/api/vehiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.patente").value("AB1234"));
    }

    @Test
    public void testEliminar() throws Exception {
        // Given
        when(vehiculoService.obtenerPorId(1L)).thenReturn(Optional.of(vehiculoDTO));
        doNothing().when(vehiculoService).eliminar(1L);

        // When & Then
        mockMvc.perform(delete("/api/vehiculos/1"))
                .andExpect(status().isNoContent());

        verify(vehiculoService, times(1)).eliminar(1L);
    }

    @Test
    public void testEliminarNoEncontrado() throws Exception {
        // Given
        when(vehiculoService.obtenerPorId(99L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(delete("/api/vehiculos/99"))
                .andExpect(status().isNotFound());
    }
}
