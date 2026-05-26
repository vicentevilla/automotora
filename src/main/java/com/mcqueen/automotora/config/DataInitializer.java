package com.mcqueen.automotora.config;

import com.mcqueen.automotora.model.TipoVehiculo;
import com.mcqueen.automotora.model.Vehiculo;
import com.mcqueen.automotora.Repository.TipoVehiculoRepository;
import com.mcqueen.automotora.Repository.VehiculoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TipoVehiculoRepository tipoVehiculoRepository;
    private final VehiculoRepository vehiculoRepository;

    @Override
    public void run(String... args) {
        if (tipoVehiculoRepository.count() > 0) {
            return;
        }
        TipoVehiculo sedan  = tipoVehiculoRepository.save(new TipoVehiculo(null, "Sedan",  "Automovil de 4 puertas"));
        TipoVehiculo suv    = tipoVehiculoRepository.save(new TipoVehiculo(null, "SUV",    "Vehiculo todoterreno"));
        TipoVehiculo camion = tipoVehiculoRepository.save(new TipoVehiculo(null, "Camion", "Vehiculo de carga"));

        vehiculoRepository.save(new Vehiculo(null, "AB1234", "Toyota",    "Corolla",  2020, new BigDecimal("15000000"), sedan));
        vehiculoRepository.save(new Vehiculo(null, "CD5678", "Honda",     "CR-V",     2021, new BigDecimal("22000000"), suv));
        vehiculoRepository.save(new Vehiculo(null, "EF9012", "Ford",      "Ranger",   2019, new BigDecimal("18500000"), camion));
        vehiculoRepository.save(new Vehiculo(null, "GH3456", "Chevrolet", "Spark",    2022, new BigDecimal("9800000"),  sedan));
        vehiculoRepository.save(new Vehiculo(null, "IJ7890", "Nissan",    "Frontier", 2023, new BigDecimal("25000000"), camion));
    }
}
