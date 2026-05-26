package com.mcqueen.automotora.DTO;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoRequestDTO {

    @NotBlank(message = "La patente es obligatoria")
    @Size(min = 6, max = 6, message = "La patente debe tener exactamente 6 caracteres")
    private String patente;
    
    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;

    @NotNull(message = "El año es obligatorio")
    @Min(value = 1900, message = "El año no es válido")
    private int anio;

    @Positive(message = "El precio debe ser mayor a 0")
    @NotNull(message = "El precio es obligatorio")
    private BigDecimal precio;

    @NotNull(message = "El tipo de vehículo es obligatorio")
    private Long tipoVehiculoId;
}