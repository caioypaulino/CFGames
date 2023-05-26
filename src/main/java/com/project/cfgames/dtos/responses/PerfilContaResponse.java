package com.project.cfgames.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.cfgames.entities.Perfil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilContaResponse {
    private Long id;
    private String email;
    private String senha;
    private Set<Perfil> perfis;
    private String enabled;
}
