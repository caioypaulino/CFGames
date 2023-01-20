package com.project.cfgames.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
