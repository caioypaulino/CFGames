package com.project.cfgames.strategies;

import com.project.cfgames.entities.Endereco;

public class StrategyEndereco {
    
    public boolean ruaValidate(Endereco endereco) {
        if (endereco.getRua().isBlank()) {
            return false;
        }
        return true;
    }

    public boolean bairroValidate(Endereco endereco) {
        if (endereco.getBairro().isBlank()) {
            return false;
        }
        return true;
    }

    public boolean cidadeValidate(Endereco endereco) {
        if (endereco.getCidade().isBlank()) {
            return false;
        }
        return true;
    }

    public boolean estadoValidate(Endereco endereco) {
        if (endereco.getEstado().isBlank()) {
            return false;
        }
        return true;
    }

    public boolean cepValidate(Endereco endereco) {
        if (endereco.getCep().isBlank() && endereco.getCep().matches("[0-9]*")) {
            return false;
        }
        return true;
    }

    public boolean paisValidate(Endereco endereco) {
        if (endereco.getPais().isBlank()) {
            return false;
        }
        return true;
    }

    public boolean allValidates(Endereco endereco) {
        if (ruaValidate(endereco) && bairroValidate(endereco) && cidadeValidate(endereco) && estadoValidate(endereco) && cepValidate(endereco) && paisValidate(endereco)) {
            return true;
        }
        else {
            return false;
        }
    }

}
