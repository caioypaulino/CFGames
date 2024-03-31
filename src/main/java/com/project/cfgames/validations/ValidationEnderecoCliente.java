package com.project.cfgames.validations;

import com.project.cfgames.dtos.requests.EnderecoClienteRequest;
import com.project.cfgames.entities.Cliente;
import com.project.cfgames.entities.enums.TipoEndereco;
import com.project.cfgames.entities.relations.EnderecoCliente;
import com.project.cfgames.exceptions.CustomValidationException;
import com.project.cfgames.repositories.EnderecoClienteRepository;
import com.project.cfgames.repositories.PedidoRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ValidationEnderecoCliente {
    @Autowired
    EnderecoClienteRepository enderecoClienteRepository;
    @Autowired
    PedidoRepository pedidoRepository;

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

    // valida se endereço cliente já existe ao realizar update
    @SneakyThrows
    public void cadastroValidate (List<EnderecoCliente> enderecosCliente) {
        if (enderecosCliente.size() > 3) {
            throw new CustomValidationException("Quantidade de Endereços Cliente inválida.");
        }
        else if (enderecosCliente.size() == 2) {
            Set<TipoEndereco> tipos = new HashSet<>();

            for (EnderecoCliente enderecoCliente : enderecosCliente) {
                if (!tipos.add(enderecoCliente.getTipo())) {
                    throw new CustomValidationException("Tipos de endereço duplicados.");
                }
            }
        }
        else if (enderecosCliente.size() == 1 && enderecosCliente.get(0).getTipo() != TipoEndereco.GERAL) {
            throw new CustomValidationException("Tipo de endereço incorreto para apenas um cadastro (não é GERAL).");
        }
    }

    // valida se endereço cliente já existe ao realizar update
    @SneakyThrows
    public void updateValidate (Cliente cliente, EnderecoClienteRequest enderecoCliente, Long id) {
        if (!enderecoClienteRepository.selectEnderecoCliente(enderecoCliente.getNumero(), cliente.getId(), enderecoCliente.getEndereco().getCep(), id).isEmpty()) {
            throw new CustomValidationException("Endereço Cliente já existente.");
        }
    }

    public void allValidates (EnderecoCliente enderecoCliente) {
        clienteValidate(enderecoCliente);
        enderecoClienteValidate(enderecoCliente);
    }
}
