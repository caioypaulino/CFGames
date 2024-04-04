package com.project.cfgames.clients.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageRequest {
    private double height;
    private double width;
    private double length;
    private double weight;
}
