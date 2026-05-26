package com.mcqueen.automotora.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoResponseDTO {
    private Long id;
    private String patente;
    private String marca;
    private String modelo;
    private int anio;
    private BigDecimal precio;
    private String tipoVehiculoNombre;
}