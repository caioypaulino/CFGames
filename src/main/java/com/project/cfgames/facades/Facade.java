package com.project.cfgames.facades;


import com.project.cfgames.entities.Cliente;
import com.project.cfgames.entities.Endereco;
import com.project.cfgames.strategies.StrategyCliente;
import com.project.cfgames.strategies.StrategyEndereco;

public class Facade {
    
    StrategyCliente strategyCliente = new StrategyCliente();

    StrategyEndereco strategyEndereco = new StrategyEndereco();

    public Cliente validaCliente(Cliente cliente) {

        if (strategyCliente.allValidates(cliente)) {
            return cliente;
        }
        else {
            throw new RuntimeException("Erro cadastro Cliente");
        }
    }

    public Endereco validaEndereco(Endereco endereco) {

        if (strategyEndereco.allValidates(endereco)) {
            return endereco;
        }
        else {
            throw new RuntimeException("Erro cadastro Endereco");
        }
    }
}
