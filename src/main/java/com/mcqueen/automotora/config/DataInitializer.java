package com.mcqueen.automotora.config;

<<<<<<< HEAD
import com.mcqueen.automotora.model.*;
import com.mcqueen.automotora.Repository.*;
=======
import com.mcqueen.automotora.model.TipoVehiculo;
import com.mcqueen.automotora.model.Vehiculo;
import com.mcqueen.automotora.Repository.TipoVehiculoRepository;
import com.mcqueen.automotora.Repository.VehiculoRepository;
>>>>>>> a6ed02af7276b2b2d3dd4413e2b7b68c13b1135c

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
<<<<<<< HEAD
import java.time.LocalDate;
=======
>>>>>>> a6ed02af7276b2b2d3dd4413e2b7b68c13b1135c

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TipoVehiculoRepository tipoVehiculoRepository;
    private final VehiculoRepository vehiculoRepository;
<<<<<<< HEAD
    private final VendedorRepository vendedorRepository;
    private final ClienteRepository clienteRepository;
    private final VentaRepository ventaRepository;
=======
>>>>>>> a6ed02af7276b2b2d3dd4413e2b7b68c13b1135c

    @Override
    public void run(String... args) {
        if (tipoVehiculoRepository.count() > 0) {
            return;
        }
        TipoVehiculo sedan  = tipoVehiculoRepository.save(new TipoVehiculo(null, "Sedan",  "Automovil de 4 puertas"));
        TipoVehiculo suv    = tipoVehiculoRepository.save(new TipoVehiculo(null, "SUV",    "Vehiculo todoterreno"));
        TipoVehiculo camion = tipoVehiculoRepository.save(new TipoVehiculo(null, "Camion", "Vehiculo de carga"));

<<<<<<< HEAD
        Vehiculo v1 = vehiculoRepository.save(new Vehiculo(null, "AB1234", "Toyota",    "Corolla",  2020, new BigDecimal("15000000"), sedan));
        Vehiculo v2 = vehiculoRepository.save(new Vehiculo(null, "CD5678", "Honda",     "CR-V",     2021, new BigDecimal("22000000"), suv));
        vehiculoRepository.save(new Vehiculo(null, "EF9012", "Ford",      "Ranger",   2019, new BigDecimal("18500000"), camion));
        vehiculoRepository.save(new Vehiculo(null, "GH3456", "Chevrolet", "Spark",    2022, new BigDecimal("9800000"),  sedan));
        vehiculoRepository.save(new Vehiculo(null, "IJ7890", "Nissan",    "Frontier", 2023, new BigDecimal("25000000"), camion));

        // ── VENDEDORES ────────────────────────────────
        Vendedor vend1 = vendedorRepository.save(new Vendedor(null, "Carlos Pérez",  "12345678-9", "carlos@automotora.cl",  "+56912345678"));
        Vendedor vend2 = vendedorRepository.save(new Vendedor(null, "Ana González",  "98765432-1", "ana@automotora.cl",     "+56987654321"));

        // ── CLIENTES ──────────────────────────────────
        Cliente cli1 = clienteRepository.save(new Cliente(null, "Pedro Rojas",   "11111111-1", "pedro@gmail.com",   "+56911111111"));
        Cliente cli2 = clienteRepository.save(new Cliente(null, "María Torres",  "22222222-2", "maria@gmail.com",   "+56922222222"));

        // ── VENTAS ────────────────────────────────────
        ventaRepository.save(new Venta(null, LocalDate.now(), v1.getPrecio(), "COMPLETADA", vend1, cli1, v1));
        ventaRepository.save(new Venta(null, LocalDate.now(), v2.getPrecio(), "COMPLETADA", vend2, cli2, v2));
=======
        vehiculoRepository.save(new Vehiculo(null, "AB1234", "Toyota",    "Corolla",  2020, new BigDecimal("15000000"), sedan));
        vehiculoRepository.save(new Vehiculo(null, "CD5678", "Honda",     "CR-V",     2021, new BigDecimal("22000000"), suv));
        vehiculoRepository.save(new Vehiculo(null, "EF9012", "Ford",      "Ranger",   2019, new BigDecimal("18500000"), camion));
        vehiculoRepository.save(new Vehiculo(null, "GH3456", "Chevrolet", "Spark",    2022, new BigDecimal("9800000"),  sedan));
        vehiculoRepository.save(new Vehiculo(null, "IJ7890", "Nissan",    "Frontier", 2023, new BigDecimal("25000000"), camion));
>>>>>>> a6ed02af7276b2b2d3dd4413e2b7b68c13b1135c
    }
}
