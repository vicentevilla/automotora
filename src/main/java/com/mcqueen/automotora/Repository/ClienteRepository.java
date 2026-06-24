package com.mcqueen.automotora.Repository;

import com.mcqueen.automotora.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByRut(String rut);
}
