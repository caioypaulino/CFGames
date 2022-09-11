package com.project.cfgames.helper;

import com.project.cfgames.entities.Cliente;

public class HelperCliente {

    public boolean nameIsNull(Cliente cliente) {
        if (cliente.getNome() == " " || cliente.getNome() == null) {
            return false;
        }
        return true;
    }

    public boolean cpfValido(Cliente cliente) {
        if (!cliente.getCpf().matches("[0-9]*")){
            return false;
        }
        return true;
    }

    public boolean cpfIsComplete(Cliente cliente) {
        if (cliente.getCpf().toString().length() != 11){
            return false;
        }
        return true;
    }

    public boolean senhaIsShort(Cliente cliente) {
        if (cliente.getSenha().toString().length() != 11){
            return false;
        }
        return true;
    }
}
