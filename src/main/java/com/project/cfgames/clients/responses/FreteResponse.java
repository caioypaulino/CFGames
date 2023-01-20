package com.project.cfgames.clients.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreteResponse {
    public String ceporigem;
    public String cepdestino;
    public String valorpac;
    public String prazopac;
    public String valorsedex;
    public String prazosedex;
}
