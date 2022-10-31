package com.project.cfgames.strategies;

import com.project.cfgames.entities.Cliente;

public class StrategyCliente {

    public boolean nomeValidate(Cliente cliente) {

        String[] string = cliente.getNome().split("\\s+");

        if (cliente.getNome().isBlank()) {
            throw new RuntimeException("Nome vazio");
        }
        else if (string.length < 2) {
            throw new RuntimeException("Nome incompleto");
        }
        else if (cliente.getNome().matches("[0-9]*")) {
            throw new RuntimeException("Nome inválido");
        } 
        else {
            return true;
        }
    }

    public boolean cpfValidate(Cliente cliente) {
        if (cliente.getCpf().isBlank()) {
            throw new RuntimeException("Cpf está vázio");
        } 
        else if (!cliente.getCpf().matches("[0-9]*")) {
            throw new RuntimeException("Cpf inválido");
        } 
        else if (cliente.getCpf().length() != 11) {
            throw new RuntimeException("Cpf inválido");
        } 
        else {
            return true;
        }
    }

    public boolean senhaValidate(Cliente cliente) {
        if (cliente.getSenha().matches("[0-9]*")) {
            throw new RuntimeException("Senha não possui números");
        } 
        else if (cliente.getSenha().length() < 8) {
            throw new RuntimeException("Senha muito curta");
        } 
        else {
            return true;
        }
    }

    public boolean telefoneValidate(Cliente cliente) {
        if (cliente.getTelefone().isBlank()) {
            throw new RuntimeException("Campo telefone está vázio");
        } 
        else if (!cliente.getTelefone().matches("[0-9]*")) {
            throw new RuntimeException("Campo telefone só pode conter números");
        }

        return true;
    }

    public boolean allValidates(Cliente cliente) {
        if (nomeValidate(cliente) && cpfValidate(cliente) && senhaValidate(cliente) && telefoneValidate(cliente)) {
            return true;
        } 
        else {
            return false;
        }
    }
}
