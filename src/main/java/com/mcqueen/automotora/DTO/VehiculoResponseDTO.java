package com.mcqueen.automotora.DTO;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

public class VehiculoResponseDTO
extends RepresentationModel<VehiculoResponseDTO>{

    private Long id;
    private String patente;
    private String marca;
    private String modelo;
    private Integer anio;
    private BigDecimal precio;
    private String tipoVehiculo;

    public VehiculoResponseDTO(){}

    public VehiculoResponseDTO(
            Long id,
            String patente,
            String marca,
            String modelo,
            Integer anio,
            BigDecimal precio,
            String tipoVehiculo){

        this.id=id;
        this.patente=patente;
        this.marca=marca;
        this.modelo=modelo;
        this.anio=anio;
        this.precio=precio;
        this.tipoVehiculo=tipoVehiculo;
    }

    public Long getId(){ return id; }
    public void setId(Long id){ this.id=id; }

    public String getPatente(){ return patente; }
    public void setPatente(String patente){ this.patente=patente; }

    public String getMarca(){ return marca; }
    public void setMarca(String marca){ this.marca=marca; }

    public String getModelo(){ return modelo; }
    public void setModelo(String modelo){ this.modelo=modelo; }

    public Integer getAnio(){ return anio; }
    public void setAnio(Integer anio){ this.anio=anio; }

    public BigDecimal getPrecio(){ return precio; }
    public void setPrecio(BigDecimal precio){ this.precio=precio; }

    public String getTipoVehiculo(){ return tipoVehiculo; }
    public void setTipoVehiculo(String tipoVehiculo){
        this.tipoVehiculo=tipoVehiculo;
    }
}