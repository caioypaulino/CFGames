package com.project.cfgames.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EnderecoDTO {
    public String code;
    public String state;
    public String city;
    public String district;
    public String address;
    public int status;
    public boolean ok;
    public String statusText;
}
