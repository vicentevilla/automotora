package com.mcqueen.automotora.repository;

import com.mcqueen.automotora.model.Vehiculo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long>{

    // Para verificar si existe un vehículo con una patente dada.
    boolean existsByPatente(String patente);
    // Para buscar vehículos por marca.
    @Query("SELECT v FROM Vehiculo v WHERE v.marca = ?1")
    List<Vehiculo> findByMarca(String marca);
    // Para buscar vehículos por año de fabricación.
    @Query("SELECT v FROM Vehiculo v WHERE v.anio = ?1")
    List<Vehiculo> findByAnio(Integer anio);
    // Para buscar vehículos por tipo de vehículo.
    @Query("SELECT v FROM Vehiculo v WHERE v.tipoVehiculo.id = :tipoID")
    List<Vehiculo> findByTipoVehiculoId(@Param("tipoID") Long tipoId);
}
