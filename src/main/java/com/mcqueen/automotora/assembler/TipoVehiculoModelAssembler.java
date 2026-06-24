package com.mcqueen.automotora.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.mcqueen.automotora.Controller.TipoVehiculoController;
import com.mcqueen.automotora.model.TipoVehiculo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class TipoVehiculoModelAssembler implements RepresentationModelAssembler<TipoVehiculo, EntityModel<TipoVehiculo>> {

    @Override
    public EntityModel<TipoVehiculo> toModel(TipoVehiculo tipo) {
        return EntityModel.of(tipo,
                linkTo(methodOn(TipoVehiculoController.class).obtenerPorId(tipo.getId())).withSelfRel(),
                linkTo(methodOn(TipoVehiculoController.class).obtenerTodos()).withRel("tipos"));
    }
}
