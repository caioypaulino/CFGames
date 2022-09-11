package com.project.cfgames.helper;

import com.project.cfgames.entities.Cliente;

public class HelperCliente extends Helper{

    public boolean nomeValidate(Cliente cliente) {
        if (stringIsNull(cliente.getNome())) {
            throw new RuntimeException("Nome vazio");
        }
        else if (cliente.getNome().matches("[0-9]*")){
            throw new RuntimeException("Nome inválido");
        }
        else {
            return true; 
        }
    }

    public boolean cpfValido(Cliente cliente) {
        if (!cliente.getCpf().matches("[0-9]*")) {
            throw new RuntimeException("Cpf inválido");
        }
        else {
            return true; 
         }
    }

    public boolean cpfIsComplete(Cliente cliente) {
        if (cliente.getCpf().length() != 11) {
            throw new RuntimeException("Cpf incompleto");
        }
        else {
           return true; 
        }
        
    }

    public boolean senhaIsShort(Cliente cliente) {
        if (cliente.getSenha().length() < 8) {
            throw new RuntimeException("Senha curta");
        }
        else {
            return true;
        }
    }

    public boolean allValidates(Cliente cliente) {
        if(nomeValidate(cliente) && cpfValido(cliente) && cpfIsComplete(cliente) && senhaIsShort(cliente))
        {
            return true;
        }
        else {
            return false;
        }
    }
}
