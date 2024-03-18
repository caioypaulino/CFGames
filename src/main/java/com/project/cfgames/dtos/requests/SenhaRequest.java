package com.project.cfgames.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SenhaRequest {
    @NotBlank(message = "Campo não informado!")
    private String senhaAtual;
    @Length(min = 8, message = "Senha muito curta (Mínimo de 8 caracteres).")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$", message = "Senha fraca ou inválida (Deve conter pelo menos: 1 Letra maiúscula e minúscula, 1 Número, 1 Caracter especial(Exemplos: $*&@#)")
    private String senha;
    @NotBlank(message = "Campo não informado!")
    @Transient
    private String confirmaSenha;
}
