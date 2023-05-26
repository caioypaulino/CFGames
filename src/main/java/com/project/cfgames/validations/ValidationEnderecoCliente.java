package com.project.cfgames.validations;

import com.project.cfgames.entities.Cliente;
import com.project.cfgames.entities.relations.EnderecoCliente;
import com.project.cfgames.exceptions.CustomValidationException;
import com.project.cfgames.repositories.ClienteRepository;
import com.project.cfgames.repositories.EnderecoClienteRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class ValidationEnderecoCliente {
    @Autowired
    EnderecoClienteRepository enderecoClienteRepository;

    // valida cliente
    @SneakyThrows
    public void clienteValidate (EnderecoCliente enderecoCliente) {
        if (enderecoCliente.getCliente() == null) {
            throw new CustomValidationException("Campo Cliente não inserido.");
        }
        else if (!enderecoClienteRepository.selectEnderecoCliente(enderecoCliente.getNumero(), enderecoCliente.getCliente().getId(), enderecoCliente.getEndereco().getCep()).isEmpty()) {
            throw new CustomValidationException("Endereço Cliente já existente.");
        }
    }

    // valida se endereço cliente já existe
    @SneakyThrows
    public void enderecoClienteValidate (EnderecoCliente enderecoCliente) {
        if (!enderecoClienteRepository.selectEnderecoCliente(enderecoCliente.getNumero(), enderecoCliente.getCliente().getId(), enderecoCliente.getEndereco().getCep()).isEmpty()) {
            throw new CustomValidationException("Endereço Cliente já existente.");
        }
    }

    // valida se endereço pertence ao cliente
    @SneakyThrows
    public void pertenceValidate (Cliente cliente, EnderecoCliente enderecoCliente) {
        if (!cliente.getId().equals(enderecoCliente.getCliente().getId())) {
            throw new CustomValidationException("Endereço não pertence ao Cliente.");
        }
    }

    public void allValidates (EnderecoCliente enderecoCliente) {
        clienteValidate(enderecoCliente);
        enderecoClienteValidate(enderecoCliente);
    }
}
