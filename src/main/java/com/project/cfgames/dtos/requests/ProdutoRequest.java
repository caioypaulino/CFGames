package com.project.cfgames.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.cfgames.entities.Categoria;
import com.project.cfgames.entities.enums.Plataforma;
import com.project.cfgames.entities.enums.StatusProduto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequest {
    private String titulo;
    private String descricao;
    // enum
    private Plataforma plataforma;
    @PastOrPresent(message = "Data de lançamento inválida.")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataLancamento;
    private String marca;
    private String publisher;
    @Min(value = 1, message = "Peso inválido.")
    // peso em gramas
    private Integer peso;
    @DecimalMin(value = ".1", message = "Comprimento inválido.")
    private Float comprimento;
    @DecimalMin(value = ".1", message = "Altura inválida.")
    private Float altura;
    @DecimalMin(value = ".1", message = "Largura inválida.")
    private Float largura;
    @Pattern(regexp = "^[0-9]{13}$", message = "Código de barras inválido (necessário 13 dígitos e apenas números).")
    private String codigoBarras;
    @DecimalMin(value = "10.0", message = "Preço inválido (mínimo R$10,00).")
    private Float preco;
    // enum
    private StatusProduto status;
    private Set<Categoria> categorias;
}
