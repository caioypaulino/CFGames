package com.project.cfgames.dtos.responses;

import com.project.cfgames.entities.Perfil;
import com.project.cfgames.entities.enums.Genero;
import com.project.cfgames.entities.relations.EnderecoCliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {
    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private Genero genero;
    private String telefone;
    private String email;
    private String senha;
    private Set<EnderecoCliente> enderecos;
    private Set<Perfil> perfis;
}
