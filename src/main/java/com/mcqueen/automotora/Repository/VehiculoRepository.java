package com.mcqueen.automotora.Repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mcqueen.automotora.DTO.VehiculoResponseDTO;
import com.mcqueen.automotora.model.Vehiculo;
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long>{

    List<VehiculoResponseDTO> findByMarcaContainingIgnoreCase(String marca);

    List<VehiculoResponseDTO> findByModeloContainingIgnoreCase(String modelo);

    List<VehiculoResponseDTO> findByPrecioLessThan(BigDecimal precio);

    List<VehiculoResponseDTO> findByAnio(int anio);

    ////
     @Query("SELECT v FROM Vehiculo v WHERE v.tipo.id = :tipoId")
    List<VehiculoResponseDTO> findByTipoId(@Param("tipoId") Long tipoId);


    @Query("SELECT v FROM Vehiculo v WHERE v.precio BETWEEN :min AND :max")
    List<VehiculoResponseDTO> buscarPorRangoPrecio(
            @Param("min") BigDecimal min,
            @Param("max") BigDecimal max);

    @Query(
        value = "SELECT * FROM vehiculos WHERE marca LIKE CONCAT('%', :marca, '%')",
        nativeQuery = true
    )
    List<VehiculoResponseDTO> buscarMarcaNativo(@Param("marca") String marca);
}
