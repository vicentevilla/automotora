package com.mcqueen.automotora.service;


import com.mcqueen.automotora.DTO.VehiculoResponseDTO;
import com.mcqueen.automotora.DTO.VehiculoRequestDTO;
import com.mcqueen.automotora.model.Vehiculo;
import com.mcqueen.automotora.model.TipoVehiculo;
import com.mcqueen.automotora.Repository.VehiculoRepository;
import com.mcqueen.automotora.Repository.TipoVehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.mcqueen.automotora.Controller.VehiculoController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehiculoService {
    
    private final VehiculoRepository vehiculoRepository;

    private final TipoVehiculoRepository tipoVehiculoRepository;

    private VehiculoResponseDTO mapToDTO(Vehiculo vehiculo){

    VehiculoResponseDTO dto =
            new VehiculoResponseDTO(
                    vehiculo.getId(),
                    vehiculo.getPatente(),
                    vehiculo.getMarca(),
                    vehiculo.getModelo(),
                    vehiculo.getAnio(),
                    vehiculo.getPrecio(),
                    vehiculo.getTipoVehiculo().getNombre()
            );

    dto.add(
            linkTo(
                    methodOn(VehiculoController.class)
                            .obtenerPorId(
                                    vehiculo.getId()
                            )
            ).withSelfRel()
    );

    dto.add(
            linkTo(
                    methodOn(VehiculoController.class)
                            .obtenerTodos()
            ).withRel("lista")
    );

    return dto;
}

    public List<VehiculoResponseDTO> obtenerTodos() {
        return vehiculoRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<VehiculoResponseDTO> obtenerPorId(Long id){
        return vehiculoRepository.findById(id).map(this::mapToDTO);
    }
// Crear nuevo vehículo
    public VehiculoResponseDTO guardar(VehiculoRequestDTO dto){
        if (vehiculoRepository.existsByPatente(dto.getPatente().toUpperCase())) {
            throw new RuntimeException("Ya existe un vehículo con la patente: " + dto.getPatente());
        }

        TipoVehiculo tipo = tipoVehiculoRepository
                .findById(dto.getTipoVehiculoId())
                .orElseThrow(() -> new RuntimeException(
                        "TipoVehiculo no encontrado con id: " + dto.getTipoVehiculoId()));

        Vehiculo vehiculo = new Vehiculo(
                null,
                dto.getPatente().toUpperCase(),
                dto.getMarca(),
                dto.getModelo(),
                dto.getAnio(),
                dto.getPrecio(),
                tipo
        );

        Vehiculo guardado = vehiculoRepository.save(vehiculo);
        return mapToDTO(guardado);
    }

// Actualizar vehiculo existente
    public Optional<VehiculoResponseDTO> actualizar(Long id, VehiculoRequestDTO dto){

        return vehiculoRepository.findById(id).map(existente -> {
            //Si cambia la patente, verifica que no esté en uso
            String nuevaPatente = dto.getPatente().toUpperCase();
            if (!existente.getPatente().equals(nuevaPatente)
                    && vehiculoRepository.existsByPatente(nuevaPatente)) {
                throw new RuntimeException("Ya existe un vehículo con la patente: " + nuevaPatente);
            }

            TipoVehiculo tipo = tipoVehiculoRepository.findById(dto.getTipoVehiculoId()).orElseThrow
            (() -> new RuntimeException("TipoVehiculo no encontrado con id: " + dto.getTipoVehiculoId()));

            existente.setPatente(nuevaPatente);
            existente.setMarca(dto.getMarca());
            existente.setModelo(dto.getModelo());
            existente.setAnio(dto.getAnio());
            existente.setPrecio(dto.getPrecio());
            existente.setTipoVehiculo(tipo);

            Vehiculo actualizado = vehiculoRepository.save(existente);
            return mapToDTO(actualizado);
        });
    }

    public void eliminar(Long id) {
        vehiculoRepository.deleteById(id);
    }

//@Query del Repository

    public List<VehiculoResponseDTO> buscarPorMarca(String marca) {
        return vehiculoRepository.findByMarca(marca)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<VehiculoResponseDTO> buscarPorAnioDesde(Integer anio) {
        return vehiculoRepository.findByAnio(anio)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<VehiculoResponseDTO> buscarPorTipo(Long tipoId) {
        return vehiculoRepository.findByTipoVehiculoId(tipoId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}