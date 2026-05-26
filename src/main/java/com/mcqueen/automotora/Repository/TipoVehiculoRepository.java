package com.mcqueen.automotora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mcqueen.automotora.model.TipoVehiculo;

// JPARepository tiene metodos crud predefinidos
public interface TipoVehiculoRepository extends JpaRepository<TipoVehiculo, Long>{
}
