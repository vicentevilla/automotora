package com.mcqueen.automotora.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.mcqueen.automotora.Controller.VendedorController;
import com.mcqueen.automotora.model.Vendedor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class VendedorModelAssembler implements RepresentationModelAssembler<Vendedor, EntityModel<Vendedor>> {

    @Override
    public EntityModel<Vendedor> toModel(Vendedor vendedor) {
        return EntityModel.of(vendedor,
                linkTo(methodOn(VendedorController.class).obtenerPorId(vendedor.getId())).withSelfRel(),
                linkTo(methodOn(VendedorController.class).obtenerTodos()).withRel("vendedores"));
    }
}
