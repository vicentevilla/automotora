package com.mcqueen.automotora.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.mcqueen.automotora.Controller.ClienteController;
import com.mcqueen.automotora.model.Cliente;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<Cliente, EntityModel<Cliente>> {

    @Override
    public EntityModel<Cliente> toModel(Cliente cliente) {
        return EntityModel.of(cliente,
                linkTo(methodOn(ClienteController.class).obtenerPorId(cliente.getId())).withSelfRel(),
                linkTo(methodOn(ClienteController.class).obtenerTodos()).withRel("clientes"));
    }
}
