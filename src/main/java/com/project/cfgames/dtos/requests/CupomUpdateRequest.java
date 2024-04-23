package com.project.cfgames.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CupomUpdateRequest {
    @DecimalMin(value = "1.0", message = "Valor mínimo de R$1,00!")
    private Float valorDesconto;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime validade;
}
