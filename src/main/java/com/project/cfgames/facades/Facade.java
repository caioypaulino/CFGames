package com.project.cfgames.facades;


import com.project.cfgames.entities.Cliente;
import com.project.cfgames.entities.Endereco;
import com.project.cfgames.validations.ValidationCliente;
import com.project.cfgames.validations.StrategyEndereco;

public class Facade {
    
    ValidationCliente validationCliente = new ValidationCliente();

    StrategyEndereco strategyEndereco = new StrategyEndereco();

    public Endereco validaEndereco(Endereco endereco) {

        if (strategyEndereco.allValidates(endereco)) {
            return endereco;
        }
        else {
            throw new RuntimeException("Erro cadastro Endereco");
        }
    }
}
