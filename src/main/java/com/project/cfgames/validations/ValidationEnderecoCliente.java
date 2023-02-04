package com.project.cfgames.validations;

import com.project.cfgames.entities.relations.EnderecoCliente;
import com.project.cfgames.validations.exceptions.CustomValidationException;
import com.project.cfgames.repositories.ClienteRepository;
import com.project.cfgames.repositories.EnderecoClienteRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationEnderecoCliente {

    @Autowired
    EnderecoClienteRepository enderecoClienteRepository;
    @Autowired
    ClienteRepository clienteRepository;

    // valida cliente
    @SneakyThrows
    public void clienteValidate (EnderecoCliente enderecoCliente) {
        if (clienteRepository.findById(enderecoCliente.getCliente().getId()).isEmpty()) {
            throw new CustomValidationException("Cliente não encontrado pelo id: " + enderecoCliente.getCliente().getId());
        }
    }

    // valida se endereço cliente já existe
    @SneakyThrows
    public void enderecoClienteValidate (EnderecoCliente enderecoCliente) {
        if (!enderecoClienteRepository.selectEnderecoCliente(enderecoCliente.getNumero(), enderecoCliente.getCliente().getId(), enderecoCliente.getEndereco().getCep()).isEmpty()) {
            throw new CustomValidationException("Endereço Cliente já existente.");
        }
    }

    public void allValidates (EnderecoCliente enderecoCliente) {
        clienteValidate(enderecoCliente);
        enderecoClienteValidate(enderecoCliente);
    }
}
