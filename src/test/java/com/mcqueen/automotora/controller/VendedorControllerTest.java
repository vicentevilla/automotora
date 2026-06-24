package com.mcqueen.automotora.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.mcqueen.automotora.Controller.VendedorController;
import com.mcqueen.automotora.assembler.VendedorModelAssembler;
import com.mcqueen.automotora.model.Vendedor;
import com.mcqueen.automotora.service.VendedorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

@WebMvcTest(VendedorController.class)
@Import(VendedorModelAssembler.class)
public class VendedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VendedorService vendedorService;

    @Autowired
    private ObjectMapper objectMapper;

    private Vendedor vendedor;

    @BeforeEach
    void setUp() {
        vendedor = new Vendedor(1L, "Carlos Pérez", "12345678-9", "carlos@automotora.cl", "+56912345678");
    }

    @Test
    public void testObtenerTodos() throws Exception {
        // Given
        when(vendedorService.obtenerTodos()).thenReturn(List.of(vendedor));

        // When & Then
        mockMvc.perform(get("/api/vendedores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.vendedorList[0].nombre").value("Carlos Pérez"))
                .andExpect(jsonPath("$._embedded.vendedorList[0].rut").value("12345678-9"));
    }

    @Test
    public void testObtenerPorId() throws Exception {
        // Given
        when(vendedorService.obtenerPorId(1L)).thenReturn(Optional.of(vendedor));

        // When & Then
        mockMvc.perform(get("/api/vendedores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Carlos Pérez"));
    }

    @Test
    public void testCrear() throws Exception {
        // Given
        when(vendedorService.guardar(any(Vendedor.class))).thenReturn(vendedor);

        // When & Then
        mockMvc.perform(post("/api/vendedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vendedor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Carlos Pérez"));
    }

    @Test
    public void testEliminar() throws Exception {
        // Given
        when(vendedorService.obtenerPorId(1L)).thenReturn(Optional.of(vendedor));
        doNothing().when(vendedorService).eliminar(1L);

        // When & Then
        mockMvc.perform(delete("/api/vendedores/1"))
                .andExpect(status().isNoContent());

        verify(vendedorService, times(1)).eliminar(1L);
    }
}
