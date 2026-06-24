package com.mcqueen.automotora.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.mcqueen.automotora.Controller.VehiculoController;
import com.mcqueen.automotora.DTO.VehiculoResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class VehiculoModelAssembler implements RepresentationModelAssembler<VehiculoResponseDTO, EntityModel<VehiculoResponseDTO>> {

    @Override
    public EntityModel<VehiculoResponseDTO> toModel(VehiculoResponseDTO vehiculo) {
        return EntityModel.of(vehiculo,
                linkTo(methodOn(VehiculoController.class).obtenerPorId(vehiculo.getId())).withSelfRel(),
                linkTo(methodOn(VehiculoController.class).obtenerTodos()).withRel("vehiculos"),
                linkTo(methodOn(VehiculoController.class).buscarPorMarca(vehiculo.getMarca())).withRel("por-marca"));
    }
}
