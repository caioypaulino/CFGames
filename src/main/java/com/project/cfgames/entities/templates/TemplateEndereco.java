package com.project.cfgames.entities.templates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TemplateEndereco {
    public String code;
    public String state;
    public String city;
    public String district;
    public String address;
    public int status;
    public boolean ok;
    public String statusText;
}
