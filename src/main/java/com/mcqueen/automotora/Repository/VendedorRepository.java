package com.mcqueen.automotora.Repository;

import com.mcqueen.automotora.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    boolean existsByRut(String rut);
}
