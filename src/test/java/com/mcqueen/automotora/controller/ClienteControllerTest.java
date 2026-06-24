package com.mcqueen.automotora.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.mcqueen.automotora.Controller.ClienteController;
import com.mcqueen.automotora.assembler.ClienteModelAssembler;
import com.mcqueen.automotora.model.Cliente;
import com.mcqueen.automotora.service.ClienteService;
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

@WebMvcTest(ClienteController.class)
@Import(ClienteModelAssembler.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1L, "Pedro Rojas", "11111111-1", "pedro@gmail.com", "+56911111111");
    }

    @Test
    public void testObtenerTodos() throws Exception {
        // Given
        when(clienteService.obtenerTodos()).thenReturn(List.of(cliente));

        // When & Then
        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.clienteList[0].nombre").value("Pedro Rojas"))
                .andExpect(jsonPath("$._embedded.clienteList[0].rut").value("11111111-1"));
    }

    @Test
    public void testObtenerPorId() throws Exception {
        // Given
        when(clienteService.obtenerPorId(1L)).thenReturn(Optional.of(cliente));

        // When & Then
        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Pedro Rojas"))
                .andExpect(jsonPath("$.rut").value("11111111-1"));
    }

    @Test
    public void testObtenerPorIdNoEncontrado() throws Exception {
        // Given
        when(clienteService.obtenerPorId(99L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/clientes/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCrear() throws Exception {
        // Given
        when(clienteService.guardar(any(Cliente.class))).thenReturn(cliente);

        // When & Then
        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Pedro Rojas"));
    }

    @Test
    public void testEliminar() throws Exception {
        // Given
        when(clienteService.obtenerPorId(1L)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteService).eliminar(1L);

        // When & Then
        mockMvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isNoContent());

        verify(clienteService, times(1)).eliminar(1L);
    }
}
