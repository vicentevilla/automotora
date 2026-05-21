package com.mcqueen.automotora.DTO;

import java.math.BigDecimal;

import com.mcqueen.automotora.model.TipoVehiculo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoResponseDTO {

    private Long id;
    private String patente;
    private String Modelo;
    private int anio;
    private String marca;
    private BigDecimal precio;
    private TipoVehiculo tipo;

}
