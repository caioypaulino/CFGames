package com.project.cfgames.clients.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreteRequest {
    private AddressRequest from;
    private AddressRequest to;
    @JsonProperty("package")
    private PackageRequest packageRequest;
}

