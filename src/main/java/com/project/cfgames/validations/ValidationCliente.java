package com.project.cfgames.validations;

import com.project.cfgames.dtos.requests.ClienteRequest;
import com.project.cfgames.dtos.requests.DadosPessoaisRequest;
import com.project.cfgames.dtos.requests.EmailRequest;
import com.project.cfgames.dtos.requests.SenhaRequest;
import com.project.cfgames.entities.Cliente;
import com.project.cfgames.exceptions.CustomValidationException;
import com.project.cfgames.repositories.ClienteRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class ValidationCliente {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    // valida idade (18+)
    @SneakyThrows
    public void idadeValidate(Cliente cliente) {
        if ((Period.between(cliente.getDataNascimento(), LocalDate.now()).getYears()) < 18) {
            throw new CustomValidationException("Data de nascimento deve conter idade maior ou igual a 18 anos.");
        }
    }

    @SneakyThrows
    public void updateIdadeValidate(ClienteRequest request) {
        if ((Period.between(request.getDataNascimento(), LocalDate.now()).getYears()) < 18) {
            throw new CustomValidationException("Data de nascimento deve conter idade maior ou igual a 18 anos.");
        }
    }

    @SneakyThrows
    public void updateIdadeValidate(DadosPessoaisRequest request) {
        if ((Period.between(request.getDataNascimento(), LocalDate.now()).getYears()) < 18) {
            throw new CustomValidationException("Data de nascimento deve conter idade maior ou igual a 18 anos.");
        }
    }

    // valida email
    @SneakyThrows
    public void emailValidate(Cliente cliente) {
        if (clienteRepository.findByEmail(cliente.getEmail()) != null) {
            throw new CustomValidationException("Email já existente.");
        }
    }

    @SneakyThrows
    public void updateEmailValidate(ClienteRequest request, Cliente cliente) {
        if (clienteRepository.findByEmailUpdate(cliente.getId(), request.getEmail()) != null) {
            throw new CustomValidationException("Email já existente.");
        }
    }

    @SneakyThrows
    public void updateEmailValidate(EmailRequest request, Cliente cliente) {
        if (request.getEmail().matches(cliente.getEmail())) {
            throw new CustomValidationException("Email idêntico ao atual.");
        }
        if (clienteRepository.findByEmailUpdate(cliente.getId(), request.getEmail()) != null) {
            throw new CustomValidationException("Email já existente.");
        }
    }

    // valida confirmação de senha
    @SneakyThrows
    public void confirmaSenhaValidate(Cliente cliente) {
        if (cliente.getConfirmaSenha() == null) {
            throw new CustomValidationException("Confirmação Senha null.");
        }
        else if (!(cliente.getSenha().matches(cliente.getConfirmaSenha()))) {
            throw new CustomValidationException("Senha e Confirmação Senha devem ser iguais.");
        }
    }

    @SneakyThrows
    public void updateSenhaValidate(ClienteRequest request) {
        if (request.getConfirmaSenha() == null) {
            throw new CustomValidationException("Confirmação Senha null.");
        }
        else if (!(request.getSenha().matches(request.getConfirmaSenha()))) {
            throw new CustomValidationException("Senha e Confirmação Senha devem ser iguais.");
        }
    }

    // valida altera senha
    @SneakyThrows
    public void updateSenhaValidate(Cliente cliente, SenhaRequest request) {
        if (!passwordEncoder.matches(request.getSenhaAtual(), clienteRepository.findSenhaByCliente(cliente.getId()))) {
            throw new CustomValidationException("Senha Atual Incorreta.");
        }
        if (!(request.getSenha().matches(request.getConfirmaSenha()))) {
            throw new CustomValidationException("Senha e Confirmação Senha devem ser iguais.");
        }
    }

    public void allValidates(Cliente cliente) {
        idadeValidate(cliente);
        emailValidate(cliente);
        confirmaSenhaValidate(cliente);
    }

    public void updateAllValidates(ClienteRequest request, Cliente cliente) {
        if (request.getDataNascimento() != null) {
            updateIdadeValidate(request);
        }
        if (request.getEmail() != null) {
            updateEmailValidate(request, cliente);
        }
        if (request.getSenha() != null) {
            updateSenhaValidate(request);
        }
    }
}
